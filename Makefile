SERVICE = kbasefeaturevalues
SERVICE_CAPS = KBaseFeatureValues
SPEC_FILE = KBaseFeatureValues.spec
URL = https://kbase.us/services/kbasefeaturevalues
DIR = $(shell pwd)
LIB_DIR = lib
SCRIPTS_DIR = scripts
TEST_DIR = test
LBIN_DIR = bin
TARGET ?= /kb/deployment
JARS_DIR = $(TARGET)/lib/jars
EXECUTABLE_SCRIPT_NAME = run_$(SERVICE_CAPS)_async_job.sh
SUB_SERVICE_LOCAL_DIR = $(DIR)/clusterservice
SUB1_SERVICE_NAME = ClusterServicePy
SUB1_ASYNC_JOB_SCRIPT_FILE = run_ClusterServicePy_async_job.sh
SUB2_SERVICE_NAME = ClusterServiceR
SUB2_ASYNC_JOB_SCRIPT_FILE = run_ClusterServiceR_async_job.sh
STARTUP_SCRIPT_NAME = start_server.sh
TEST_SCRIPT_NAME = run_tests.sh
KB_RUNTIME ?= /kb/runtime
ANT_HOME ?= $(KB_RUNTIME)/ant
ANT = $(ANT_HOME)/bin/ant

.PHONY: test

default: compile

all: compile build build-startup-script build-executable-script build-test-script

compile:
	kb-sdk compile $(SPEC_FILE) \
		--out $(LIB_DIR) \
		--plclname $(SERVICE_CAPS)::$(SERVICE_CAPS)Client \
		--jsclname javascript/Client \
		--pyclname $(SERVICE_CAPS).$(SERVICE_CAPS)Client \
		--javasrc src \
		--java \
		--javasrv \
		--javapackage us.kbase;

build:
	$(ANT) war -Djars.dir=$(JARS_DIR)
	chmod +x $(SCRIPTS_DIR)/entrypoint.sh

build-executable-script:
	mkdir -p $(LBIN_DIR)
	$(ANT) build-executable-script -Djars.dir=$(JARS_DIR) -Dexec.cmd.file=$(EXECUTABLE_SCRIPT_NAME)
	chmod +x $(LBIN_DIR)/$(EXECUTABLE_SCRIPT_NAME)
	echo '#!/bin/bash' > $(LBIN_DIR)/$(SUB1_ASYNC_JOB_SCRIPT_FILE)
	echo 'export PYTHONPATH=$$PATH:$$PYTHONPATH' >> $(LBIN_DIR)/$(SUB1_ASYNC_JOB_SCRIPT_FILE)
	echo 'cd $(SUB_SERVICE_LOCAL_DIR)' >> $(LBIN_DIR)/$(SUB1_ASYNC_JOB_SCRIPT_FILE)
	echo 'python $(SUB1_SERVICE_NAME)Server.py $$1 $$2 $$3' >> $(LBIN_DIR)/$(SUB1_ASYNC_JOB_SCRIPT_FILE)
	chmod a+x $(LBIN_DIR)/$(SUB1_ASYNC_JOB_SCRIPT_FILE)
	echo '#!/bin/bash' > $(LBIN_DIR)/$(SUB2_ASYNC_JOB_SCRIPT_FILE)
	echo 'export R_LIBS=$(R_LIBS)' >> $(LBIN_DIR)/$(SUB2_ASYNC_JOB_SCRIPT_FILE)
	echo 'cd $(SUB_SERVICE_LOCAL_DIR)' >> $(LBIN_DIR)/$(SUB2_ASYNC_JOB_SCRIPT_FILE)
	echo 'Rscript $(SUB2_SERVICE_NAME)Impl.r $$1 $$2' >> $(LBIN_DIR)/$(SUB2_ASYNC_JOB_SCRIPT_FILE)
	chmod a+x $(LBIN_DIR)/$(SUB2_ASYNC_JOB_SCRIPT_FILE)

build-startup-script:
	mkdir -p $(LBIN_DIR)
	echo '#!/bin/bash' > $(SCRIPTS_DIR)/$(STARTUP_SCRIPT_NAME)
	echo 'script_dir=$$(dirname "$$(readlink -f "$$0")")' >> $(SCRIPTS_DIR)/$(STARTUP_SCRIPT_NAME)
	echo 'cd $(SCRIPTS_DIR)' >> $(SCRIPTS_DIR)/$(STARTUP_SCRIPT_NAME)
	echo 'java -cp $(JARS_DIR)/jetty/jetty-start-7.0.0.jar:$(JARS_DIR)/jetty/jetty-all-7.0.0.jar:$(JARS_DIR)/servlet/servlet-api-2.5.jar \
		-DKB_DEPLOYMENT_CONFIG=$$script_dir/../deploy.cfg -Djetty.port=5000 org.eclipse.jetty.start.Main jetty.xml' >> $(SCRIPTS_DIR)/$(STARTUP_SCRIPT_NAME)
	chmod +x $(SCRIPTS_DIR)/$(STARTUP_SCRIPT_NAME)

build-test-script:
	echo '#!/bin/bash' > $(TEST_DIR)/$(TEST_SCRIPT_NAME)
	echo 'script_dir=$$(dirname "$$(readlink -f "$$0")")' >> $(TEST_DIR)/$(TEST_SCRIPT_NAME)
	echo 'export KB_DEPLOYMENT_CONFIG=$$script_dir/../deploy.cfg' >> $(TEST_DIR)/$(TEST_SCRIPT_NAME)
	echo 'export KB_AUTH_TOKEN=`cat /kb/module/work/token`' >> $(TEST_DIR)/$(TEST_SCRIPT_NAME)
	echo 'export JAVA_HOME=$(JAVA_HOME)' >> $(TEST_DIR)/$(TEST_SCRIPT_NAME)
	echo '$(ANT) test -Djars.dir=$(JARS_DIR)' >> $(TEST_DIR)/$(TEST_SCRIPT_NAME)
	chmod +x $(TEST_DIR)/$(TEST_SCRIPT_NAME)

test:
	if [ ! -f /kb/module/work/token ]; then echo -e '\nOutside a docker container please run "kb-sdk test" rather than "make test"\n' && exit 1; fi
	bash $(TEST_DIR)/$(TEST_SCRIPT_NAME)

clean:
	rm -rfv $(LBIN_DIR)
	
