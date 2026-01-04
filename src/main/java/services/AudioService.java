package services;

import base.APIControlActions;
import base.ScreeningControl;
import entity.AudioPojo.AudioAnswerPayLoad;
import entity.AudioPojo.AudioFileGeneratorPayLoad;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utility.JavaToJSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static base.ScreeningControl.jobApplicationId;
import static base.ScreeningControl.jobRoleId;

public class AudioService extends APIControlActions {

    @Step("Submit audio answer for question ID: {questionId} with experience: {experience}")
    public Response submitAudioAnswer(String questionId, String experience) {
        Response audioFileResponse = generateAudioURL();
        audioFileResponse.then().log().all();
        String audioFileId = audioFileResponse.jsonPath().getString("fileId");
        String audioFileUploadURL = audioFileResponse.jsonPath().getString("azureUpload.uploadUrl");
        System.out.println(audioFileId);
        System.out.println(audioFileUploadURL);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        setHeaders(headers);

        String payload = getAudioServicePayLoad(questionId, experience, audioFileId);
        setBody(payload);

        Response audioAnswerResponse = executePatchAPI("/api/candidateScreening/update-candidate-result/"+ScreeningControl.candidateScreeningId);

        return  audioAnswerResponse;
    }

    @Step("Generate audio file upload URL")
    private Response generateAudioURL() {
        AudioFileGeneratorPayLoad payload = AudioFileGeneratorPayLoad.builder()
                .originalFilename("recordedAudio.webm")
                .fileType("audio")
                .mimeType("audio/webm")
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        setHeaders(headers);

        String jsonPayLoad = JavaToJSON.convertToJSON(payload);
        setBody(jsonPayLoad);

        return executePostAPI("/api/candidateScreening/generate-upload-url");
    }

    @Step("Build audio answer payload for question ID: {questionId}")
    private String getAudioServicePayLoad(String questionId, String experience, String audioFileId) {
        AudioAnswerPayLoad audioAnswerPayLoad = AudioAnswerPayLoad.builder()
                .questionId(questionId)
                .type("audio")
                .skill("api")
                .jobRoleId(ScreeningControl.jobRoleId)
                .experience(experience)
                .jobApplicationId(ScreeningControl.jobApplicationId)
                .timeSpent(75)
                .fullScreenExitCount(0)
                .tabSwitchCount(1)
                .candidateAnswer(null)
                .answerAudioFileId("audio_file_987654")
                .retakes(0)
                .copyPasteAnalysis(
                        AudioAnswerPayLoad.CopyPasteAnalysis.builder()
                                .totalDuration(90)
                                .totalCopyEvents(2)
                                .questionCopyCount(1)
                                .optionCopyCount(0)
                                .fullQuestionCopyCount(1)
                                .hasQuestionCopying(true)
                                .hasOptionCopying(false)
                                .hasFullQuestionCopying(true)
                                .riskScore(40)
                                .riskLevel("low")
                                .isSuspicious(false)
                                .sessionId("audio_1766289626529")
                                .analysisVersion("1.0")
                                .timestamp("2025-12-21T04:15:30.000Z")
                                .copyBreakdown(
                                        AudioAnswerPayLoad.CopyBreakdown.builder()
                                                .questionCopies(
                                                        AudioAnswerPayLoad.QuestionCopies.builder()
                                                                .count(1)
                                                                .averageLength(35)
                                                                .timestamps(List.of())
                                                                .build()
                                                )
                                                .optionCopies(
                                                        AudioAnswerPayLoad.OptionCopies.builder()
                                                                .count(0)
                                                                .averageLength(0)
                                                                .timestamps(List.of())
                                                                .build()
                                                )
                                                .fullQuestionCopies(
                                                        AudioAnswerPayLoad.FullQuestionCopies.builder()
                                                                .count(1)
                                                                .averageLength(95)
                                                                .timestamps(List.of())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .hasCopyPasteAnalysis(true)
                .build();
        return JavaToJSON.convertToJSON(audioAnswerPayLoad);
    }


}
