package services;

import base.APIControlActions;
import base.ScreeningControl;
import entity.ProgrammingPojo.ProgrammingRootPayLoad;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utility.JavaToJSON;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProgrammingService extends APIControlActions {
    @Step("Submit programming answer for question ID: {questionId} with experience: {experience}")
    public Response submitProgrammingAnswer(String questionId, String experience, String candidateAnswer) {
        ProgrammingRootPayLoad programmingRootPayLoad = ProgrammingRootPayLoad.builder()
                .questionId(questionId)
                .type("programming")
                .skill("api")
                .jobRoleId(ScreeningControl.jobRoleId)
                .experience(experience)
                .jobApplicationId(ScreeningControl.jobApplicationId)
                .timeSpent(120)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .candidateAnswer(candidateAnswer)
                .typingAnalysis(
                        ProgrammingRootPayLoad.TypingAnalysis.builder()
                                .totalDuration(120000)
                                .totalCharacters(250)
                                .keystrokeCount(300)
                                .pasteEventCount(1)
                                .copyEventCount(0)
                                .focusLossCount(3)
                                .pasteAnalysis(
                                        ProgrammingRootPayLoad.PasteAnalysis.builder()
                                                .totalPasteEvents(1)
                                                .totalPastedCharacters(50)
                                                .pastePercentage(20)
                                                .rawPastePercentage(20)
                                                .largestPaste(50)
                                                .hasCodePatterns(true)
                                                .hasFormatting(true)
                                                .riskLevel("medium")
                                                .build())
                                .typingAnalysis(
                                        ProgrammingRootPayLoad.TypingAnalysis.builder()
                                                .averageTypingSpeed(2.5)
                                                .typingBursts(5)
                                                .totalKeystrokes(300)
                                                .backspaceCount(10)
                                                .riskLevel("medium")
                                                .build())
                                .focusAnalysis(
                                        ProgrammingRootPayLoad.FocusAnalysis.builder()
                                                .totalFocusEvents(6)
                                                .focusLossCount(3)
                                                .focusChangeFrequency(0)
                                                .riskLevel("medium")
                                                .build())
                                .qualityAnalysis(
                                        ProgrammingRootPayLoad.QualityAnalysis.builder()
                                                .wordCount(200)
                                                .averageWordsPerMinute(40.0)
                                                .hasProperStructure(true)
                                                .hasVariedVocabulary(true)
                                                .qualityScore(80)
                                                .riskLevel("low")
                                                .build())
                                .globalEventAnalysis(
                                        ProgrammingRootPayLoad.GlobalEventAnalysis.builder()
                                                .globalCopyCount(0)
                                                .questionCopyCount(0)
                                                .externalInteractionCount(2)
                                                .hasQuestionCopying(false)
                                                .hasHighRiskCopying(false)
                                                .copySourceDistribution(
                                                        ProgrammingRootPayLoad.CopySourceDistribution.builder()
                                                                                             .build())
                                                .suspiciousPatternCount(0)
                                                .riskLevel("low")
                                                .build())
                                .copyPasteCorrelations(
                                        ProgrammingRootPayLoad.CopyPasteCorrelations.builder()
                                               .totalCorrelations(0)
                                               .questionPasteCount(0)
                                               .averageTimeBetween(0)
                                               .riskLevel("low")
                                               .build())
                                .riskScore(25)
                                .sessionId("typing_1766289753076_4vsscmczy")
                                .analysisVersion("2.0-enhanced")
                                .timestamp("2024-06-20T12:34:56Z")
                                .privacyCompliant(true)
                                .build())

                .hasTypingAnalysis(true)
                .screeningTestId("692d3aeb3d9838750c2d650a")
                .programmingLanguageId(62)
                .editorEvents(Collections.emptyList())
                .retakes(0)
                .build();

        System.out.println(JavaToJSON.convertToJSON(programmingRootPayLoad));

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        setHeaders(headers);

        String jsonPayLoad = JavaToJSON.convertToJSON(programmingRootPayLoad);
        setBody(jsonPayLoad);

        return executePatchAPI("/api/candidateScreening/update-candidate-result/"+ScreeningControl.candidateScreeningId);

    }
}
