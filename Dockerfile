FROM openjdk
EXPOSE 8091
ADD /target/CarLoanService.jar /CarLoanService.jar
ENTRYPOINT ["java","-jar","/CarLoanService.jar"]