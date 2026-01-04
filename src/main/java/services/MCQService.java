package services;

import base.APIControlActions;
import base.ScreeningControl;
import entity.MCQPojo.MCQRootPayLoad;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utility.JavaToJSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCQService extends APIControlActions {
    @Step("Submit MCQ answer for question ID: {questionId} with experience: {experience}")
    public Response submitMCQAnswer(String questionId, String experience, String candidateAnswer) {
        MCQRootPayLoad.QuestionCopies questionCopies = MCQRootPayLoad.QuestionCopies.builder()
                .count(0)
                .averageLength(0)
                .timestamps(List.of())
                .build();

        MCQRootPayLoad.OptionCopies optionCopies = MCQRootPayLoad.OptionCopies.builder()
                .count(0)
                .averageLength(0)
                .timestamps(List.of())
                .build();

        MCQRootPayLoad.FullQuestionCopies fullQuestionCopies = MCQRootPayLoad.FullQuestionCopies.builder()
                .count(0)
                .averageLength(0)
                .timestamps(List.of())
                .build();

        MCQRootPayLoad payload = MCQRootPayLoad.builder()
                .questionId(questionId)
                .type("mcq")
                .skill("api")
                .jobRoleId(ScreeningControl.jobRoleId)
                .experience(experience)
                .jobApplicationId(ScreeningControl.jobApplicationId)
                .timeSpent(9)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .candidateAnswer(candidateAnswer)
                .copyPasteAnalysis(
                        MCQRootPayLoad.CopyPasteAnalysis.builder()
                                .totalDuration(257)
                                .totalCopyEvents(0)
                                .questionCopyCount(0)
                                .optionCopyCount(0)
                                .fullQuestionCopyCount(0)
                                .hasQuestionCopying(false)
                                .hasOptionCopying(false)
                                .hasFullQuestionCopying(false)
                                .riskScore(0)
                                .riskLevel("low")
                                .isSuspicious(false)
                                .copyBreakdown(
                                        MCQRootPayLoad.CopyBreakdown.builder()
                                                .questionCopies(questionCopies)
                                                .optionCopies(optionCopies)
                                                .fullQuestionCopies(fullQuestionCopies)
                                                .build())
                                .sessionId("copypaste_1766289737523_yq3hhq6i6")
                                .analysisVersion("2.0-mcq-focused")
                                .timestamp("2025-12-21T04:02:25.095Z")
                                .build())
                .hasCopyPasteAnalysis(true)
                .build();
        //System.out.println(JavaToJSON.convertToJSON(payload));

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        setHeaders(headers);

        String jsonPayLoad = JavaToJSON.convertToJSON(payload);
        setBody(jsonPayLoad);

        return executePatchAPI("/api/candidateScreening/update-candidate-result/"+ScreeningControl.candidateScreeningId);
    }
}