FROM kbase/sdkbase2:latest
MAINTAINER KBase Developer
# -----------------------------------------

# Insert apt-get instructions here to install
# any required dependencies for your module.

RUN apt-get update && apt-get install -y \ 
  build-essential \
  python-dev \
  python-setuptools \
  python-numpy \
  python-scipy \
  libatlas-dev \
  libatlas3-base
RUN pip install scikit-learn

# install R dependencies
RUN sudo apt-get update && \
    yes '' | sudo apt-get -y install r-base && \
    yes '' | sudo apt-get -y install r-base-dev && \
    R --version

RUN sudo apt-get -y install libnlopt-dev && \
    R -q -e 'if(!require(nloptr)) install.packages("nloptr", repos="http://cran.us.r-project.org", dependencies=TRUE)' && \
    R -q -e 'if(!require(lme4)) install.packages("lme4", repos="http://cran.us.r-project.org", dependencies=TRUE)' && \
    R -q -e 'if(!require(cluster)) install.packages("cluster", repos="http://cran.us.r-project.org", dependencies=TRUE)' && \
    R -q -e 'if(!require(jsonlite)) install.packages("jsonlite", repos="http://cran.us.r-project.org")' && \
    R -q -e 'if(!require(clValid)) install.packages("clValid", repos="http://cran.us.r-project.org")' && \
    R -q -e 'if(!require(amap)) install.packages("amap", repos="http://cran.us.r-project.org")' && \
    R -q -e 'if(!require(sp)) install.packages("sp", repos="http://cran.us.r-project.org")' && \
    R -q -e 'if(!require(ape)) install.packages("ape", dependencies=TRUE, repos="http://cran.us.r-project.org")' && \
    R -q -e 'if(!require(flashClust)) install.packages("flashClust", dependencies=TRUE, repos="http://cran.us.r-project.org")' && \
    R -q -e 'if(!require(fpc)) install.packages("fpc", dependencies=TRUE, repos="http://cran.us.r-project.org")'

# -----------------------------------------

COPY ./ /kb/module
RUN mkdir -p /kb/module/work
RUN chmod -R a+rw /kb/module

WORKDIR /kb/module

RUN make all

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]
