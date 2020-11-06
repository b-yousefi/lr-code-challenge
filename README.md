# Take-Home Code Challenge for Labregister Fullstack Engineer Position

## Instructions

Work on this challenge under your own github account. Please create the repo on your account first and then create a PR with your solution.

## Background

Scientists need a digital solution to store and identify various items used in their experiments. These items come in different forms and might include samples, chemicals, devices, etc...

This repo provides a simple, Spring Boot app for representing those items.

### Starting point

You will see a minimal frontend when you run the application and go to `http://localhost:8080`.

There are two existing REST API endpoints:

```
  POST /items
  GET /items  
```

Item structure is as follows:
```
  Item
    id: string - auto-generated uuid string
    name: string - mandatory name of the item
    attributes: map<string, string> - optional key-value pairs
    creationDate: date - auto-generated creation date
```

### Running the app

Start the application by typing

	./gradlew bootRun

This will start up a Spring Boot application with Tomcat server running on 8080.

Show all other possible gradle tasks:

	./gradlew tasks

When working on frontend changes, it's also possible to keep Tomcat running in one terminal and simultaneously use any of the frontend task in another terminal. For possible frontend tasks, see the frontend's [aurelia-app/README.md](aurelia-app/README.md) or change to the `aurelia-app/` folder and run `yarn start` to get a (very) basic list of frontend development tasks.

## The Challenge

Please add two new API endpoints providing the following functionality:
- Create a new endpoint to update an existing item
- Create a new endpoint to fetch versions of a specific item

As this is a fullstack development challenge, you should also modify the existing frontend in such a way as to utilize these new endpoints. The frontend requirements are open-ended; however, a full UI might include the ability for a user to update an item, see the history for an item, etc...

## Acceptance Criteria

- The challenge is purposefully open-ended. As long as you stay in the scope of the tasks above, you may make any improvements / cleanup / code changes that you find necessary. Our only expectation is that you should be able to clearly explain your decisions.

- We understand that not everyone has the same amount of "extra" time to devote to completing this challenge. It is up to you to determine the amount of time you spend on the exercise. In general, we are not expecting you to spend more than 4-8 hours in this challenge. So that the reviewer understands how you are defining the scope of work, please clearly indicate your solution (including the points you would work on if you had more time) for the task in the PR description along with any other pertinent information.

- The whole idea of this exercise is to understand how you approach software development. Assume you are writing production code. Your solution should indicate your way of working.

## How to submit your solution
- Please upload the provided repo to your github account and create a PR with your solution. 

Let us know if you have any questions, and we look forward to seeing your approach.

Good Luck!
