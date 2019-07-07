# Step-Up

Soha Glal and Ashraf Khalil

suha.glal@gmail.com,ashraf.khalil@adu.ac.ae

Abu Dhabi University

This project was under the supervison of Dr. Ashraf Khalil

## Introduction

StepUp, a step counter application. StepUp exploits off-the-shelf, sensor-enabled mobile phones to automatically infer the number of steps the user walked. We discuss results of the deployment of StepUp on off-the-shelf mobile phones. We implemented StepUp on Nokia N95 8GB phones. The application is written in Symbian C++ and Java.

![Alt text](/img/backend.png?raw=true "StepUp backend software architecture ")

The StepUp backend software architecture is shown in the Figure above. All software components are written in symbian C++ and we use a java server to service the application requests from the StepUp portal, the mobile client, and Facebook. Communications between the phone and the backend uses remote procedure calls implemented by socket connection. Requests are handled by Java server in combination with a MySQL database for storage. Data exchange between the phone and the backend is initiated by the phone at timed intervals whenever the phone has data to up load. The data is uploaded through socket connection. Once the data is received at the backend they are inserted into the MySQL database.
The user and the team members progress is represented in the mobile client software,the StepUp web portal and the facebook. StepUp publishes team members’ progress by means of either a “pull” or “push” approach. A popular application such as Facebook requires a push approach. This allows content to be inserted via some variant of a HTTP transported markup language (e.g., FBML, XML). The StepUp backend supports pull based data publishing by exposing a standard web service based API. This API is used to support the data needs of StepUp  web portal. Push-based publishing is supported by the PushConnector component shown in the Figure above. This component handles the generic operation of pushing a team member progress on to the other team members’ facebook accounts.
The Periodic classifier: is executed every day and every week and every month to give statistic about the best team member, the best group ,the best user.

![Alt text](/img/frontend.png?raw=true "StepUp frontend software architecture ")
The figure above shows the StepUp software architecture for the Nokia N95 phone. The phone architecture comprises the following software components (all components are written in symbian c++): 
Data Filter module:
The acceleration data obtained from the accelerometer sensor of Nokia N95 8GB was very noisy therefore we added a data filter which reduced the noise significantly. The technique used in the data filter module is to create a circular buffer (a ring buffer) and continuously fill it with data produced by the sensor. As a result, "raw" sensor data is not used, but an average value of the buffer contents is used instead. This effectively smoothes the readings, which is perceived in practice as reduction of the noise.
Step detection module:
The step detection module does the following: 
1- Computes the slope between every two consecutive points in the data buffer and saves the slope value in a new data buffer. 
2- Scans the slope’s data buffer and counts the number of positive slopes followed by negative slopes whenever it identifies a change in the slope from a positive value to a negative value. 
When the number of positive slopes is roughly the same as the negative slopes, it identifies a step. 
3- Adds the detected step to the step counter if they are more than one. If it only detected one step, this means the user is not walking because walking produces consecutive sine waves. 
Upload manager:
This component is responsible for establishing connections to the backend servers in an opportunistic way, depending on radio link availability which can be either cellular or WiFi. It also uploads the number of steps walked and the user information from local storage and tears down the connection after the data is transferred. 
Download manager:
This component is responsible for getting information from the backend servers according to the user requests, these information includes the current progress of the team members or any historical information about your team members performance in the past days, weeks or months. 

A complete description of the application can be found in the following scientific papers:
1. https://www.researchgate.net/profile/S_Glal/publication/224120282_StepUp_A_step_counter_mobile_application_to_promote_healthy_lifestyle/links/575e692c08ae9a9c955a7c32.pdf
2. https://www.sciencedirect.com/science/article/pii/S074756321300246X
