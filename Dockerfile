FROM kbase/sdkbase:latest

MAINTAINER KBase Developer
# -----------------------------------------

# Insert apt-get instructions here to install
# any required dependencies for your module.

RUN echo fo

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


RUN add-apt-repository ppa:openjdk-r/ppa \
	&& sudo apt-get update \
	&& sudo apt-get -y install openjdk-8-jdk \
	&& echo java versions: \
	&& java -version \
	&& javac -version \
	&& echo $JAVA_HOME \
	&& ls -l /usr/lib/jvm \
	&& cd /kb/runtime \
	&& rm java \
	&& ln -s /usr/lib/jvm/java-8-openjdk-amd64 java \
	&& ls -l

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

#RUN cd /kb/dev_container/modules/jars \
#	&& git pull

# update jars
RUN cd /kb/dev_container/modules/jars \
	&& git pull \
	&& . /kb/dev_container/user-env.sh \
	&& make deploy

COPY ./ /kb/module
RUN mkdir -p /kb/module/work
RUN chmod 777 /kb/module

WORKDIR /kb/module

RUN make all

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]
