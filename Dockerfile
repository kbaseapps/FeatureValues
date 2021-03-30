FROM kbase/sdkbase2:latest

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
  libatlas3-base
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

# update jars
RUN cd /tmp \
	&& git clone https://github.com/kbase/workspace_deluxe \
	&& cd workspace_deluxe \
	&& cp -vr lib/* /kb/deployment/lib/ \
	&& cd /tmp \
	&& git clone https://github.com/kbase/auth \
	&& cd auth \
	&& cp -vr python-libs/biokbase /kb/deployment/lib/ \
	&& cp -vr Bio-KBase-Auth/lib/Bio /kb/deployment/lib/ \
	&& cd /tmp \
	&& git clone https://github.com/kbase/handle_mngr \
	&& cd handle_mngr \
	&& cp -vr lib/* /kb/deployment/lib/ \
	&& cd ~/src/kb_sdk \
	&& cp -vr lib/biokbase /kb/deployment/lib/ \
	&& cp -vr lib/Bio /kb/deployment/lib/ \
	&& cd /tmp \
	&& git clone https://github.com/kbase/jars \
	&& cd /tmp/jars \
	&& cp -vr lib/jars /kb/deployment/lib/ \
	&& rm -rf /tmp/* \
	&& rm -rf /root/.cpanm

# update log.py for Python3
RUN cd /tmp \
    && git clone https://github.com/kbase/sdkbase2 \
    && cd sdkbase2 \
    && git checkout python \
    && cp -v log.py /kb/deployment/lib/biokbase/
    
RUN pip install JSONRPCBase

COPY ./ /kb/module
RUN mkdir -p /kb/module/work
RUN chmod -R a+rw /kb/module

WORKDIR /kb/module

RUN make all

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]
