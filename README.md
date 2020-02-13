
Feature Values Service
=============================================

Overview
----------
This KBase service provides a set of methods for manipulation and visualization
of expression values organized as two-dimensional matrix and assigned to 
genome features on one dimension and to different sorts conditions on second 
dimension.

Notes for preparing test data
-----------------------------
The prepared test data is checked into the repository as .json files in specific test/data/ directories. These .json file will work for tests on as long as the Genome object does not incur backwards incompatible changes. The version of the checked in .json files is KBaseGenomes.Genome-17.0 from ci.kbase.us circa 2/10/2020/.


Authors
---------
Roman Sutormin, LBL (rsutormin@lbl.gov)
Marcin Joachimiak, LBL (mjoachimiak@lbl.gov)
Pavel Novichkov, LBL (psnovichkov@lbl.gov)
Srividya Ramakrishnan, CSHL (srividya.ramki@gmail.com)
Michael Sneddon, LBL (mwsneddon@lbl.gov)


Special deployment instructions
----------
* There is a dependency to R-script command in both service and AWE-worker scripts
* Pyhton Scikit-learn package is not utilized yet but it's in short-term plans
* Follow the standard KBase deployment and testing procedures (you have to specify
test user credentials in test/test.cfg file in order to be able to run "make test")


Starting/Stopping the service, and other notes
---------------------------
* to start and stop the service, use the 'start_service' and 'stop_service'
  scripts in (the default location) /kb/deployment/services/KBaseFeatureValues



