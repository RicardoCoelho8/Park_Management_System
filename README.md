# Park Management System
## Overview
This repository hosts the Park Management System, a comprehensive solution developed as part of the Software development Laboratory (LABDSOF) in our Master’s Degree program at Instituto Superior de Engenharia do Porto (ISEP). This system is designed to provide an improved customer experience for registered users. The system focuses on known customers and learns from their habits to offer them better service every day. Customers who are registered can avoid the hassle of collecting, paying for, and presenting tickets at the exit. The customers just approach the park entrance and enter the park. Likewise, they just approach the exit and leave the park. For each parking visit the customer will receive PARKY “coins” which can be redeemed to pay future parking visits.

## Team Members
Special thanks to all the team members whose dedication and expertise were crucial to the development of this system:

* Member 1: Daniel Carvalho
* Member 2: Ivo Castro
* Member 3: Nuno Rocha
* Member 4: Ricardo Coelho
* Member 5: Rui Soares

## Documentation
For more information on Park Management System, please refer to our comprehensive documentation included in the Wiki repository.        

## How to Use and Clone the System
### Prerequisites
Before you begin, ensure you have the following installed:

Git
* Docker (for running microservices)
* Node.js and npm (for the React frontend)
* Java (for springboot microservices)

### Cloning the Repository
`git clone https://github.com/RicardoCoelho8/Park_Management_System.git`

### Navigate to the cloned directory:
`cd ../Park_Management_System`

### Run the Setup and start script for microservices
`SetupAndStart.bat` or `SetupAndStart.sh` (linux users)

### Run the frontend app
`cd FrontOffice` (navigate to frontend dir)  
`npm install` (install dependencies)    
`npm start`  (start the app)   

### Run the simulation of Barriers and Display screens of one Park
`cd ..`         
`cd StartFrontEnd`     
`StartBarrierFrontEnd1.bat` or `StartBarrierFrontEnd1.sh` (linux users)      
`StartBarrierFrontEnd2.bat` or `StartBarrierFrontEnd2.sh` (linux users)     
`StartDisplayFrontEnd1.bat` or `StartDisplayFrontEnd1.sh` (linux users)     
`StartDisplayFrontEnd2.bat` or `StartDisplayFrontEnd2.sh` (linux users)     


### Component Descriptions
- **StartBarrierFrontEnd1:** Represents the entrance barrier of the park. This component controls and manages the access of vehicles as they enter the park.
- **StartBarrierFrontEnd2:** Functions as the exit barrier of the park. It manages and controls the departure of vehicles.
- **StartDisplayFrontEnd1:** Serves as a display at the park's entrance. It provides necessary information and instructions to the customers upon their arrival.
- **StartDisplayFrontEnd2:** Acts as the display at the park's exit. This component presents relevant information to the customers as they depart from the park.


### Using the app
- **Registered Users:** Log in with your credentials to access the system.
- **New Users:** Sign up to create an account and start using the parking services.
- **Pre-Registered Test Users (Username : Password):**
  1. **Park Manager:** parkmanager@isep.ipp.pt : 123PasswordX#
  2. **Customer Manager:** customermanager@isep.ipp.pt : 123PasswordX#
  3. **User:** leoKim@isep.ipp.pt : 123PasswordX#
  4. **User:** mayaGupta@isep.ipp.pt : 123PasswordX#
  5. **User:** ruiSoares@isep.ipp.pt : 123PasswordX#

**Note:** The frontend app interface is optimized for specific devices: the Customer and Park Manager screens are designed for a tablet view, while the User screen is tailored for mobile devices. For the best user experience, we recommend adjusting your browser window size or using the browser's DevTools to simulate a tablet or mobile screen when accessing these different sections of the application.


### Simulate the entrance and exit of a vehicle in the park
- For **entrance**, enter a registered license plate number in the **StartBarrierFrontEnd1** component (entrance barrier).
- For **exit**, do the same in the **StartBarrierFrontEnd2** component (exit barrier).

# Additional Information
The features and components presented in this system are part of a simulation client developed for LABDSOFT in our Master's program. Please be aware that not all functionalities may be fully implemented or finished. This project serves as a prototype and an academic exercise, showcasing the potential capabilities and design for a park management system. As such, some aspects of the system are conceptual and may require further development for real-world application.
