# recruitment_task
# Github Branches Fetcher Application
The Github Branches Fetcher application is a simple Spring Boot REST API example that allows you to retrieve information about a user's GitHub repositories and branches.

## Configuration

Before running the application, you need to perform a few configuration steps.

### Step 1: Generate a Personal GitHub Token
To access the GitHub API, you need to generate a personal access token. Here are the steps to do so:
Log in to your GitHub account.
1. Click on your profile picture in the upper-right corner and select "Settings."

2. Navigate to the "Developer settings" section and select "Personal access tokens."

3. Click on "Generate token" and choose the appropriate access permissions. For this application, you'll need at least the "public_repo" or "repo" permission, depending on the repositories you want to access.

4. Generate the token and save it in a secure location, as it won't be visible again once you leave the page.

### Step 2: Configure the GitHub Token in the Application
After generating the GitHub token, you need to configure it in the application's application.properties file.

1. Open the application.properties file (located in src/main/resources) in your project.

2. Add the following line to the application.properties file, replacing YOUR_GITHUB_TOKEN with your generated token:

github.access.token=YOUR_GITHUB_TOKEN

3. Save the application.properties file.

### Step 3: Running the Application
Once you have configured the GitHub token, you can run the application.

1. Start the application in your development environment or on a server.

2. The application will be accessible at http://localhost:8080/repositories (or a different port if you modify the configuration).

### Usage
The application handles GET requests at the /repositories endpoint. You can use an HTTP request tool such as curl or Postman to test the application. Here's an example of usage:

### Example with cURL
Retrieve information about the branches of a user "exampleusername" on GitHub, using the "Accept" header set to "application/json":

curl -H "Accept: application/json" http://localhost:8080/repositories?username=exampleusername

The application will return information about the branches in JSON format.

### Error Responses
If the requested user doesn't exist, the application will return the following JSON response:

{
    "status": 404,
    "message": "User doesnt exist"
}

If someone sends a request with the "Accept" header set to "application/xml" (unsupported media type), the application will return the following JSON response:

{
    "status": 406,
    "message": "Unsupported media type"
}

### Author
This application was created by Weronika Graczyk.
