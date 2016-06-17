FROM kbase/kbase:sdkbase.latest
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
  libatlas3gf-base
RUN pip install scikit-learn
RUN pip install scipy

RUN R -q -e 'if(!require(jsonlite)) install.packages("jsonlite", repos="http://cran.us.r-project.org")'
RUN R -q -e 'if(!require(clValid)) install.packages("clValid", repos="http://cran.us.r-project.org")'
RUN R -q -e 'if(!require(amap)) install.packages("amap", repos="http://cran.us.r-project.org")'
RUN R -q -e 'if(!require(sp)) install.packages("sp", repos="http://cran.us.r-project.org")'
RUN R -q -e 'if(!require(ape)) install.packages("ape", repos="http://cran.us.r-project.org")'
RUN R -q -e 'if(!require(flashClust)) install.packages("flashClust", repos="http://cran.us.r-project.org")'
RUN R -q -e 'if(!require(fpc)) install.packages("fpc", dependencies=TRUE, repos="http://cran.us.r-project.org")'
RUN R -q -e 'if(!require(cluster)) install.packages("cluster", repos="http://cran.us.r-project.org")'

# -----------------------------------------

COPY ./ /kb/module
RUN mkdir -p /kb/module/work
RUN chmod 777 /kb/module

WORKDIR /kb/module

RUN make all

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]
