FROM kbase/kbase:sdkbase.latest
MAINTAINER KBase Developer
# -----------------------------------------

# Insert apt-get instructions here to install
# any required dependencies for your module.

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update && apt-get install -y \ 
  build-essential \
  python-dev \
  python-setuptools \
  python-numpy \
  python-scipy \
  libatlas-dev \
  libatlas3gf-base
RUN pip install scikit-learn
RUN pip install scipy

# download anaconda
RUN wget https://repo.anaconda.com/archive/Anaconda3-2019.10-Linux-x86_64.sh
RUN bash Anaconda3-2019.10-Linux-x86_64.sh -b -p /root/anaconda/ && \
rm Anaconda3-2019.10-Linux-x86_64.sh

# Set path to conda
ENV PATH=/root/anaconda/bin:$PATH

RUN which conda

# Updating Anaconda packages
RUN conda update conda
RUN conda update anaconda
RUN conda update --all
RUN conda config --append channels conda-forge

# install R dependencies
RUN conda install r
RUN conda install r-amap 
RUN conda install r-jsonlite 
RUN conda install r-clValid 
RUN conda install r-sp 
RUN conda install r-ape 
RUN conda install r-flashClust 
#RUN conda install r-fpc

RUN R -q -e 'install.packages("fpc", repos="http://cran.r-project.org")'

# -----------------------------------------

COPY ./ /kb/module
RUN mkdir -p /kb/module/work
RUN chmod -R a+rw /kb/module

WORKDIR /kb/module
RUN keytool -import -keystore /usr/lib/jvm/java-7-oracle/jre/lib/security/cacerts -storepass changeit -noprompt -trustcacerts -alias letsencryptauthorityx3 -file ./ssl/lets-encrypt-x3-cross-signed.der

RUN make all

ENTRYPOINT [ "./scripts/entrypoint.sh" ]
#ENTRYPOINT ["tail", "-f", "/dev/null"]

CMD [ ]
