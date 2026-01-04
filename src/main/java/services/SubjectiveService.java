package services;

import base.APIControlActions;
import base.ScreeningControl;
import entity.SubjectivePojo.SubjectiveRootPayLoad;
import io.restassured.response.Response;
import utility.JavaToJSON;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SubjectiveService extends APIControlActions {

    public Response submitSubjectiveAnswer(String questionId, String experience, String candidateAnswer) {
        SubjectiveRootPayLoad payload = SubjectiveRootPayLoad.builder()
                .questionId(questionId)
                .type("subjective")
                .skill("api")
                .jobRoleId(ScreeningControl.jobRoleId)
                .experience(experience)
                .jobApplicationId(ScreeningControl.jobApplicationId)
                .timeSpent(60)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .candidateAnswer(candidateAnswer)
                .typingAnalysis(
                        SubjectiveRootPayLoad.TypingAnalysis.builder()
                                .totalDuration(54979)
                                .totalCharacters(103)
                                .keystrokeCount(119)
                                .pasteEventCount(0)
                                .copyEventCount(0)
                                .focusLossCount(2)
                                .pasteAnalysis(
                                        SubjectiveRootPayLoad.PasteAnalysis.builder()
                                                .totalPasteEvents(0)
                                                .totalPastedCharacters(0)
                                                .pastePercentage(0)
                                                .rawPastePercentage(0)
                                                .largestPaste(0)
                                                .hasCodePatterns(false)
                                                .hasFormatting(false)
                                                .riskLevel("low")
                                                .build())
                                .typingAnalysis(
                                        SubjectiveRootPayLoad.TypingAnalysis.builder()
                                                .averageTypingSpeed(1.87)
                                                .typingBursts(0)
                                                .totalKeystrokes(119)
                                                .backspaceCount(2)
                                                .riskLevel("low")
                                                .build())
                                .focusAnalysis(
                                        SubjectiveRootPayLoad.FocusAnalysis.builder()
                                                .totalFocusEvents(4)
                                                .focusLossCount(2)
                                                .focusChangeFrequency(0.5)
                                                .riskLevel("low")
                                                .build())
                                .qualityAnalysis(
                                        SubjectiveRootPayLoad.QualityAnalysis.builder()
                                                .wordCount(52)
                                                .averageWordsPerMinute(5.67)
                                                .hasProperStructure(true)
                                                .hasVariedVocabulary(true)
                                                .qualityScore(70)
                                                .riskLevel("medium")
                                                .build())
                                .globalEventAnalysis(
                                        SubjectiveRootPayLoad.GlobalEventAnalysis.builder()
                                                .globalCopyCount(0)
                                                .questionCopyCount(0)
                                                .externalInteractionCount(0)
                                                .hasQuestionCopying(false)
                                                .hasHighRiskCopying(false)
                                                .copySourceDistribution(Collections.emptyMap())
                                                .suspiciousPatternCount(0)
                                                .riskLevel("low")
                                                .build())
                                .copyPasteCorrelations(
                                        SubjectiveRootPayLoad.CopyPasteCorrelations.builder()
                                                .totalCorrelations(0)
                                                .questionPasteCount(0)
                                                .averageTimeBetween(0)
                                                .riskLevel("low")
                                                .build())

                                .riskScore(0)
                                .sessionId("typing_1766289626529_41cvu873s")
                                .analysisVersion("2.0-enhanced")
                                .timestamp("2025-12-21T04:01:21.509Z")
                                .privacyCompliant(true)
                                .build())
                .hasTypingAnalysis(true)
                .build();
        System.out.println(JavaToJSON.convertToJSON(payload));

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        setHeaders(headers);

        String jsonPayLoad = JavaToJSON.convertToJSON(payload);
        setBody(jsonPayLoad);

        return executePatchAPI("/api/candidateScreening/update-candidate-result/"+ScreeningControl.candidateScreeningId);
    }

    /*public static void main(String[] args) {
        SubjectiveService service = new SubjectiveService();
        service.submitSubjectiveAnswer("screening123", "question456", "role789", "4.0", "application101112", "This is a sample subjective answer.");
    }*/
}
