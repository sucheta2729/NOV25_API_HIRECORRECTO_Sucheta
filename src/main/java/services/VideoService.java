package services;

import base.APIControlActions;
import base.ScreeningControl;
import entity.VideoPojo.VideoAnswerPayLoad;
import entity.VideoPojo.VideoFileGeneratorPayLoad;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utility.JavaToJSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoService extends APIControlActions {

    @Step("Submit video answer for question ID: {questionId} with experience: {experience}")
    public Response submitVideoAnswer(String questionId, String experience) {
        Response videoFileResponse = generateVideoURL();
        String videoFileId = videoFileResponse.jsonPath().getString("fileId");
        String videoFileUploadURL = videoFileResponse.jsonPath().getString("azureUpload.uploadUrl");

        String payload = getVideoAnswerPayLoad(questionId, ScreeningControl.jobRoleId, experience, ScreeningControl.jobApplicationId, videoFileId);
        setBody(payload);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        setHeaders(headers);

        Response videoAnswerResponse = executePatchAPI("/api/candidateScreening/update-candidate-result/"+ ScreeningControl.candidateScreeningId);

        return videoAnswerResponse;
    }

    @Step("Generate video file upload URL")
    private Response generateVideoURL(){
        VideoFileGeneratorPayLoad videoFileGeneratorPayLoad = VideoFileGeneratorPayLoad.builder()
                .originalFilename("")
                .fileType("")
                .mimeType("")
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        setHeaders(headers);

        String jsonPayLoad = JavaToJSON.convertToJSON(videoFileGeneratorPayLoad);
        setBody(jsonPayLoad);

        return executePostAPI("/api/candidateScreening/generate-upload-url");
    }

    @Step("Build video answer payload for question ID: {questionId}")
    private String getVideoAnswerPayLoad(String questionId, String jobRoleId, String experience, String jobApplicationId, String answerVideoFileId) {
        VideoAnswerPayLoad videoAnswerPayLoad = VideoAnswerPayLoad.builder()
                .questionId(questionId)
                .type("video")
                .skill("api")
                .jobRoleId(jobRoleId)
                .experience(experience)
                .jobApplicationId(jobApplicationId)
                .timeSpent(60)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .candidateAnswer(null)
                .answerVideoFileId("6947714752e582fefe7e1ec3")
                .retakes(0)
                .copyPasteAnalysis(
                        VideoAnswerPayLoad.CopyPasteAnalysis.builder()
                                .totalDuration(120)
                                .totalCopyEvents(3)
                                .questionCopyCount(1)
                                .optionCopyCount(1)
                                .fullQuestionCopyCount(1)
                                .hasQuestionCopying(true)
                                .hasOptionCopying(true)
                                .hasFullQuestionCopying(true)
                                .riskScore(65)
                                .riskLevel("medium")
                                .isSuspicious(true)
                                .copyBreakdown(
                                        VideoAnswerPayLoad.CopyBreakdown.builder()
                                                .questionCopies(
                                                        VideoAnswerPayLoad.QuestionCopies.builder()
                                                                .count(1)
                                                                .averageLength(45)
                                                                .timestamps(List.of("00:01:20"))
                                                                .build())
                                                .optionCopies(
                                                        VideoAnswerPayLoad.OptionCopies.builder()
                                                                .count(1)
                                                                .averageLength(20)
                                                                .timestamps(List.of("00:02:10"))
                                                                .build())
                                                .fullQuestionCopies(
                                                        VideoAnswerPayLoad.FullQuestionCopies.builder()
                                                                .count(1)
                                                                .averageLength(120)
                                                                .timestamps(List.of("00:03:05"))
                                                                .build())
                                                .build())
                                .sessionId("video_1766289626529")
                                .analysisVersion("1.0")
                                .timestamp("2025-12-21T04:01:21.509Z")
                                .build())
                .hasCopyPasteAnalysis(true)
                .build();
        return JavaToJSON.convertToJSON(videoAnswerPayLoad);
    }
}




