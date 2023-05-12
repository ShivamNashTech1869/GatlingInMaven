# Gatling Maven Project

This is a Gatling project built with Maven.

## Description

This project demonstrates how to perform load testing using Gatling, a powerful open-source load testing tool. It includes a basic simulation class that showcases different simulation steps such as creating, updating, getting, and deleting a user.

## Prerequisites

Before running the simulation, make sure you have the following:

- Java JDK installed
- Maven installed
- Gatling installed

## Getting Started

To get started with this project, follow these steps:

1. Clone the repository:

   ```shell
   git clone https://github.com/shivamknoldus1869/GatlingInMaven.git

2. Navigate to the project directory:
 
    ```shell
   cd GatlingInMaven
  
3. Customize the simulation parameters:

Open the BasicSimulation.scala file and modify the simulation parameters according to your requirements. You can set the base URL, feeder file path, durations, number of users, etc.
 
4. Run the simulation:

    ```shell
   mvn gatling:test
  
 
This will execute the Gatling load test simulation and generate the HTML report in the target/gatling directory.

5. View the report:

Open the generated HTML report located at target/gatling in your web browser to analyze the load test results.
