package testScripts;

import base.ScreeningControl;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.*;

import java.util.ArrayList;
import java.util.List;

public class CandidateScreeningTest {

    public static String experience = "4.0";

    @Step("Execute candidate MCQ screening test")
    @Test
    public void candidateMCQScreeningTest() {
        initializeScreeningData();
        Response screeningDetailsResponse = getScreeningDetails();
        submitMCQAnswers(screeningDetailsResponse);
        submitSubjectiveAnswers(screeningDetailsResponse);
        submitProgrammingAnswers(screeningDetailsResponse);
        submitAudioAnswers(screeningDetailsResponse);
    }
    @Step("Initialize screening control with candidate data")
    private void initializeScreeningData() {
        ScreeningControl.candidateScreeningId = "694770d452e582fefe7e1ebc";
        ScreeningControl.jobRoleId = "68943b744df518afa9442034";
        ScreeningControl.jobApplicationId = "69476ed0c41f0cc9ce8110f0";
    }

    @Step("Retrieve screening details and question IDs")
    private Response getScreeningDetails() {
        ScreeningDetails screeningDetails = new ScreeningDetails();
        Response screeningDetailsResponse = screeningDetails.getScreeningDetails("692d3aeb3d9838750c2d650a");
        System.out.println(screeningDetailsResponse.asPrettyString());

        String audioQuestionID = screeningDetailsResponse.jsonPath().getString("find { it.audio != null }.audio[0]._id");
        System.out.println("Audio Question ID: " + audioQuestionID);
        String videoQuestionID = screeningDetailsResponse.jsonPath().getString("find { it.video != null }.video[0]._id");
        System.out.println("Video Question ID: " + videoQuestionID);

        return screeningDetailsResponse;
    }

    @Step("Submit all MCQ answers for candidate")
    private void submitMCQAnswers(Response screeningDetailsResponse) {
        List<String> questionIds = new ArrayList<>();
        List<String> mcqanswers = new ArrayList<>();
        {
            mcqanswers.add("JWTException");
        }
        questionIds = screeningDetailsResponse.jsonPath().getList("find { it.mcq != null }.mcq._id");
        System.out.println("MCQ Questions ID: " + questionIds);

        for (int i = 0; i < questionIds.size(); i++) {
            MCQService mcqService = new MCQService();
            Response submitMCQAnswerResponse = mcqService.submitMCQAnswer(questionIds.get(i), experience, mcqanswers.get(i));
            Assert.assertEquals(submitMCQAnswerResponse.statusCode(), 200, "Expected HTTP 200 when submitting MCQ answer");
        }
    }

    @Step("Submit all subjective answers for candidate")
    private void submitSubjectiveAnswers(Response screeningDetailsResponse) {
        List<String> subjectiveQuestionIds = new ArrayList<>();
        List<String> subjectiveAnswers = new ArrayList<>();
        {
            subjectiveAnswers.add("<p>- More Reliable</p><p>- More Secure</p><p>- Flackness is lesser then UI</p><p>- Cost Effiective Early Bugs identification</p>");
        }
        subjectiveQuestionIds = screeningDetailsResponse.jsonPath().getList("find { it.subjective != null }.subjective._id");
        System.out.println("Subjective Questions ID: " + subjectiveQuestionIds);
        for (int i = 0; i < subjectiveQuestionIds.size(); i++) {
            SubjectiveService subjectiveService = new SubjectiveService();
            Response subjectiveAsnwerResponse = subjectiveService.submitSubjectiveAnswer(subjectiveQuestionIds.get(i), experience, subjectiveAnswers.get(i));
            Assert.assertEquals(subjectiveAsnwerResponse.statusCode(), 200, "Expected HTTP 200 when submitting Subjective answer");
        }
    }

    @Step("Submit all programming answers for candidate")
    private void submitProgrammingAnswers(Response screeningDetailsResponse) {
        List<String> programmingQuestionIds = new ArrayList<>();
        List<String> programmingAnswers = new ArrayList<>();
        {
            programmingAnswers.add("import java.util.ArrayList;\n" +
                    "import java.util.Collections;\n" +
                    "import java.util.List;\n" +
                    "import java.util.Scanner;\n" +
                    "import java.util.Comparator;\n" +
                    "import java.util.stream.Collectors;\n" +
                    "\n" +
                    "class Patient {\n" +
                    "    int id;\n" +
                    "    String name;\n" +
                    "    int age;\n" +
                    "\n" +
                    "    public Patient(int id, String name, int age) {\n" +
                    "        this.id = id;\n" +
                    "        this.name = name;\n" +
                    "        this.age = age;\n" +
                    "    }\n" +
                    "\n" +
                    "    public String getName() {\n" +
                    "        return name;\n" +
                    "    }\n" +
                    "\n" +
                    "    public int getAge() {\n" +
                    "        return age;\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "public class Solution {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        Scanner scanner = new Scanner(System.in);\n" +
                    "\n" +
                    "        int N = scanner.nextInt();\n" +
                    "        List<Patient> patients = new ArrayList<>();\n" +
                    "        for (int i = 0; i < N; i++) {\n" +
                    "            int id = scanner.nextInt();\n" +
                    "            String name = scanner.next();\n" +
                    "            int age = scanner.nextInt();\n" +
                    "            patients.add(new Patient(id, name, age));\n" +
                    "        }\n" +
                    "\n" +
                    "        int minAge = scanner.nextInt();\n" +
                    "        scanner.close();\n" +
                    "\n");
        }

        programmingQuestionIds = screeningDetailsResponse.jsonPath().getList("find { it.programming != null }.programming._id");
        System.out.println("Programming Questions ID: " + programmingQuestionIds);
        for (int i = 0; i < programmingQuestionIds.size(); i++) {
            ProgrammingService programmingService = new ProgrammingService();
            Response programmingAnswerResponse = programmingService.submitProgrammingAnswer(programmingQuestionIds.get(i), experience, programmingAnswers.get(i));
            Assert.assertEquals(programmingAnswerResponse.statusCode(), 200, "Expected HTTP 200 when submitting Programming answer");
        }
    }

    @Step("Submit all audio answers for candidate")
    private void submitAudioAnswers(Response screeningDetailsResponse) {
        List<String> audienceQuestionIds = new ArrayList<>();
        audienceQuestionIds = screeningDetailsResponse.jsonPath().getList("find { it.audio != null }.audio._id");
        System.out.println("Audio Questions ID: " + audienceQuestionIds);
        for (int i = 0; i < audienceQuestionIds.size(); i++) {
            AudioService audioService = new AudioService();
            Response audioAnswerResponse = audioService.submitAudioAnswer(audienceQuestionIds.get(i), experience);
            Assert.assertEquals(audioAnswerResponse.statusCode(), 200, "Expected HTTP 200 when submitting Audio answer");
        }
    }

    @Step("Submit video answer test")
    @Test
    public void submitVideoAnswer() {
        VideoService videoService = new VideoService();
        Response videoAnswerResponse = videoService.submitVideoAnswer("692d3aee3d9838750c2d6520", experience);
        System.out.println(videoAnswerResponse.prettyPrint());
        Assert.assertEquals(videoAnswerResponse.statusCode(), 200, "Expected HTTP 200 when submitting Video answer");
    }

    @Step("Submit audio answer test")
    @Test
    public void submitAudioAnswer() {
        AudioService audioService = new AudioService();
        Response audioAnswerResponse = audioService.submitAudioAnswer("692d3aee3d9838750c2d6521", experience);
        System.out.println(audioAnswerResponse.prettyPrint());
        Assert.assertEquals(audioAnswerResponse.statusCode(), 200, "Expected HTTP 200 when submitting Video answer");
    }

    @Step("Submit programming answer test")
    @Test
    public void submitProgrammingAnswer() {
        ProgrammingService programmingService = new ProgrammingService();
        String candidateAnswer = "import java.util.ArrayList;\\nimport java.util.Collections;\\nimport java.util.List;\\nimport java.util.Scanner;\\nimport java.util.Comparator;\\nimport java.util.stream.Collectors;\\n\\nclass Patient {\\n    int id;\\n    String name;\\n    int age;\\n\\n    public Patient(int id, String name, int age) {\\n        this.id = id;\\n        this.name = name;\\n        this.age = age;\\n    }\\n\\n    public String getName() {\\n        return name;\\n    }\\n\\n    public int getAge() {\\n        return age;\\n    }\\n}\\n\\npublic class Solution {\\n    public static void main(String[] args) {\\n        Scanner scanner = new Scanner(System.in);\\n\\n        int N = scanner.nextInt();\\n        List<Patient> patients = new ArrayList<>();\\n        for (int i = 0; i < N; i++) {\\n            int id = scanner.nextInt();\\n            String name = scanner.next();\\n            int age = scanner.nextInt();\\n            patients.add(new Patient(id, name, age));\\n        }\\n\\n        int minAge = scanner.nextInt();\\n        scanner.close();\\n\\n        // TODO: Implement the solution here\\n        // Filter patients by minAge and then sort them by name.\\n        // Print the names of the filtered and sorted patients, one per line.\\n\\n        // Example placeholder for output:\\n        // List<Patient> filteredAndSortedPatients; // Populate this with your solution\\n        // for (Patient p : filteredAndSortedPatients) {\\n        //     System.out.println(p.getName());\\n        // }\\n    }\\n}";

        Response programmingAnswerResponse = programmingService.submitProgrammingAnswer("692d3aee3d9838750c2d6524", experience, candidateAnswer);
        System.out.println(programmingAnswerResponse.prettyPrint());
        Assert.assertEquals(programmingAnswerResponse.statusCode(), 200, "Expected HTTP 200 when submitting Programming answer");
    }
}
