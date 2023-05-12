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


# Report Documentation
## Load Test Report

After running the Gatling simulation, a load test report is generated in HTML format. The report provides detailed information about the load test execution and performance metrics. You can find the report at the following location:

[View Load Test Report](file:///home/knoldus/Documents/Gatling/GatlingMVN/target/gatling/basicsimulation-20230512161146498/index.html)

The report includes the following sections:

### Summary

The summary section provides an overview of the load test results, including the total number of requests, number of failed requests, response time percentiles, and throughput.

### Requests

The requests section displays individual request details, such as the request name, request count, average response time, and request details chart.

### Errors

The errors section lists any errors or failed requests that occurred during the load test. It provides information about the error message, status code, and occurrence count.

### Distribution

The distribution section presents response time distribution charts, allowing you to analyze the distribution of response times across different percentiles.

### Response Time

The response time section visualizes the response time metrics, including response time percentiles and response time distribution.

### Global Information

The global information section provides general information about the load test run, such as the start time, end time, and total duration.

### Configuration

The configuration section displays the configuration settings used for the load test, such as the base URL, feeder file path, and injection profiles.

### Assertions

The assertions section shows the results of the assertions defined in the simulation. It indicates whether the defined performance thresholds were met or not.

Use the Gatling HTML report to analyze the load test results, identify performance bottlenecks, and optimize your system accordingly.


