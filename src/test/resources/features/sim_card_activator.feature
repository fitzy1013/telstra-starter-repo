Feature: Sim Card Activation

  Background:
    Given the system is up and running

  Scenario: Activate ICCID "1255789453849037777" and verify active status
    When I activate ICCID "1255789453849037777" with customer email "test@gmail.com"
    Then the activation status for ICCID "1255789453849037777" should be "true" when I check the status at "http://localhost:8080/record/1"

  Scenario: Activate ICCID "8944500102198304826" and verify inactive status
    When I activate ICCID "8944500102198304826" with customer email "test@gmail.com"
    Then the activation status for ICCID "8944500102198304826" should be "false" when I check the status at "http://localhost:8080/record/2"
