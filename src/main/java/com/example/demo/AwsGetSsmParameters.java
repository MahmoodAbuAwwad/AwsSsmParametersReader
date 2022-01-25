package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;

@SpringBootApplication
public class AwsGetSsmParameters {

	public static void main(String[] args) throws Exception {
		String[] ssms = new String[]{
			"/dev/emr/first-time-batch-profile-matching/PROFILE_MATCHING_DB_PASSWORD",
			"/dev/emr/first-time-batch-profile-matching/RESUME_PARSING_AUDIT_DB_PASSWORD",
			"/dev/emr/first-time-batch-profile-matching/AI_SERVICES_DB_PASSWORD",
			"/dev/emr/first-time-batch-profile-matching/HARRI3_DB_PASSWORD",
			"/prod/emr/first-time-batch-profile-matching/PROFILE_MATCHING_DB_PASSWORD",
			"/prod/emr/first-time-batch-profile-matching/RESUME_PARSING_AUDIT_DB_PASSWORD",
			"/prod/emr/first-time-batch-profile-matching/AI_SERVICES_DB_PASSWORD",
			"/prod/emr/first-time-batch-profile-matching/HARRI3_DB_PASSWORD",
			"/dev/emr/normal-batch-profile-matching/PROFILE_MATCHING_DB_PASSWORD",
			"/dev/emr/normal-batch-profile-matching/RESUME_PARSING_AUDIT_DB_PASSWORD",
			"/dev/emr/normal-batch-profile-matching/AI_SERVICES_DB_PASSWORD",
			"/dev/emr/normal-batch-profile-matching/HARRI3_DB_PASSWORD",
			"/prod/emr/normal-batch-profile-matching/PROFILE_MATCHING_DB_PASSWORD",
			"/prod/emr/normal-batch-profile-matching/RESUME_PARSING_AUDIT_DB_PASSWORD",
			"/prod/emr/normal-batch-profile-matching/AI_SERVICES_DB_PASSWORD",
			"/prod/emr/normal-batch-profile-matching/HARRI3_DB_PASSWORD",
			"/dev/emr/first-time-batch-satisfaction_batch/TEAM_RDS_PASSWORD",
			"/dev/emr/first-time-batch-satisfaction_batch/AI_SERVICES_RDS_PASSWORD",
			"/prod/emr/first-time-batch-satisfaction_batch/TEAM_RDS_PASSWORD",
			"/prod/emr/first-time-batch-satisfaction_batch/AI_SERVICES_RDS_PASSWORD",
			"/dev/emr/normal-batch-satisfaction_batch/TEAM_RDS_PASSWORD",
			"/dev/emr/normal-batch-satisfaction_batch/AI_SERVICES_RDS_PASSWORD",
			"/prod/emr/normal-batch-satisfaction_batch/TEAM_RDS_PASSWORD",
			"/prod/emr/normal-batch-satisfaction_batch/AI_SERVICES_RDS_PASSWORD",
	};

		for (int i=0; i < ssms.length;i++){
			System.out.println("Value of "+ssms[i]+"\n" + getParameter(ssms[i])+"\n\n");
		}
	}
	public static String getParameter (String paraName) throws Exception {

		Region region = Region.US_EAST_1;
		SsmClient ssmClient = SsmClient.builder()
				.region(region)
				.build();

		String parameterValue = getParaValue(ssmClient, paraName);
		ssmClient.close();
		return parameterValue;
	}

	public static String getParaValue(SsmClient ssmClient, String paraName) throws Exception {
		String parameterValue="";
		try {
			GetParameterRequest parameterRequest = GetParameterRequest.builder()
					.name(paraName)
					.build();

			GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
			parameterValue= parameterResponse.parameter().value();

		} catch (SsmException e) {
			throw new Exception("Can't read ssm parameter\nError: "+e.getMessage());
		}
		return parameterValue;
	}

}
