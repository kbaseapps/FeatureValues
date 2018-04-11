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

# install R dependencies
RUN CODENAME=`grep CODENAME /etc/lsb-release | cut -c 18-` && \
    echo "deb http://cran.cnr.berkeley.edu/bin/linux/ubuntu $CODENAME/" >> /etc/apt/sources.list && \
    sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys E084DAB9 && \
    sudo apt-get update && \
    yes '' | sudo apt-get -y install r-base && \
    yes '' | sudo apt-get -y install r-base-dev && \
    echo 'install.packages(c("lme4"), repos="http://cran.us.r-project.org", dependencies=TRUE)\n' > /tmp/packages.R && \
    echo 'install.packages("cluster", dependencies=TRUE, repos="http://cran.us.r-project.org")\n' > /tmp/packages.R && \
    Rscript /tmp/packages.R
RUN R -q -e 'if(!require(jsonlite)) install.packages("jsonlite", repos="http://cran.us.r-project.org")' && \
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
RUN keytool -import -keystore /usr/lib/jvm/java-7-oracle/jre/lib/security/cacerts -storepass changeit -noprompt -trustcacerts -alias letsencryptauthorityx3 -file ./ssl/lets-encrypt-x3-cross-signed.der

RUN make all

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]
