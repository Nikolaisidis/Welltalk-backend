package com.communicators.welltalk.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.AppointmentEntity;
import com.communicators.welltalk.Entity.ReferralEntity;
import com.communicators.welltalk.Entity.ReferralEntity;

@Service
public class EmailTemplates {
    @Autowired
    private EmailService emailService;

    private String base_link = "https://well-talk-ten.vercel.app/";

    // email account has been verified
    public String sendVerificationEmail(String to) {
        String subject = "Account Verification";
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Verified</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        font-family: Arial, sans-serif;\n" +
                "        background-color: #f4f4f4;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f4f4f4; padding: 20px;\">\n"
                +
                "      <tr>\n" +
                "        <td align=\"center\">\n" +
                "          <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #ffffff; padding: 20px;\">\n"
                +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                <img src=\"cid:imageId1\" alt=\"Logo\" width=\"150\" style=\"display: block; margin: 0 auto;\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                <img src=\"cid:imageId2\" alt=\"Email\" width=\"100\" height=\"100\" style=\"display: block; margin: 0 auto;\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\">\n" +
                // " <p style=\"font-size: 15px; font-weight: bold; color: #8a252c; margin: 10px
                // 0;\">ACCOUNT IS VERIFIED</p>\n"
                // +
                "                <p style=\"font-size: 25px; font-weight: bold; color: #474647; margin: 10px 0;\">Your Account is Verified</p>\n"
                +
                "                <hr style=\"border: 1px solid #8a252c; width: 30%; margin: 10px auto;\" />\n" +
                "                <p style=\"font-size: 12px; color: #474647; line-height: 1.5; margin: 20px;\">\n" +
                "                  <span style=\"font-weight: bold; color: #8a252c;\">Congratulations!</span> Your WellTalk account has been successfully\n"
                +
                "                  verified. <br><br>You’re all set to begin exploring and enjoying seamless appointment booking with integrated journals and logs.\n"
                +
                "                  Simply click the button below to log in and start using WellTalk.\n" +
                "                </p>\n" +
                "                <a href=\"" + base_link
                + "\" style=\"background-color: #8a252c; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;\">Go to WellTalk</a>\n"
                +
                "                <p style=\"font-size: 12px; color: #474647; margin-top: 10px;\">\n" +
                "                  If the button isn't clickable, please use the following link instead:\n" +
                "                  <a href=\"" + base_link + "\" style=\"color: #8a252c;\">" + base_link + "</a>\n"
                +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        // Paths for the images you want to embed
        String image1Path = "static/images/logowords.png";
        String image2Path = "static/images/approve.png";

        try {
            // Send the email with the HTML content and images
            emailService.sendHtmlMessage(to, subject, htmlContent, image1Path, image2Path);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }

    // referral notification has been sent
    public String sendReferralInvitation(ReferralEntity referral, String token) {
        String subject = "Referral Invitation";
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Referral for Counseling Session</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        background-color: #f4f4f4;\n" +
                "        font-family: Arial, sans-serif;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f4f4f4; padding: 20px\">\n"
                +
                "      <tr>\n" +
                "        <td align=\"center\">\n" +
                "          <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #ffffff; padding: 20px\">\n"
                +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId1\" alt=\"Logo\" width=\"150\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId2\" alt=\"Referral\" width=\"100\" height=\"100\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\">\n" +
                "                <h2 style=\"font-size: 22px; font-weight: bold; color: #474647; margin: 10px 0;\">\n" +
                "                  Referral for Counseling Session\n" +
                "                </h2>\n" +
                "                <hr style=\"border: 1px solid #8a252c; width: 30%; margin: 10px auto;\" />\n" +
                "                <p style=\"font-size: 14px; color: #474647; line-height: 1.5; margin: 20px;\">\n" +
                "                  <strong>Dear " + referral.getStudentFirstName() + " " + referral.getStudentLastName()
                + ",</strong><br /><br />\n" +
                "                  You have been referred by your <strong>" + referral.getRelationship()
                + "</strong>, <strong>" + referral.getReferrer().getFirstName() + " "
                + referral.getReferrer().getLastName()
                + "</strong> for a counseling session. Please click the link below to acknowledge\n"
                +
                "                  that you have received this message of referral:\n" +
                "                </p>\n" +
                "                <a href=\"" + base_link + "referral/" + token
                + "/pendingreferral\" style=\"background-color: #8a252c; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; margin: 10px 0;\">\n"
                +
                "                  Acknowledge Referral\n" +
                "                </a>\n" +
                "                <p style=\"font-size: 12px; color: #474647; margin-top: 10px\">\n" +
                "                  If the button isn't clickable, please use the following link instead:\n" +
                "                  <a href=\"" + base_link + "referral/" + token
                + "/pendingreferral\" style=\"color: #8a252c\">" + base_link + "referral/" + token +
                "/pendingreferral</a>\n" +
                "                </p>\n" +
                "                <p style=\"font-size: 12px; color: #474647; margin: 30px 0 10px; text-align: center;\">\n"
                +
                "                  Thank you for using WellTalk. We look forward to assisting you with your counseling session!\n"
                +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        String image1Path = "static/images/logowords.png";
        String image2Path = "static/images/referral-user.png";

        try {
            emailService.sendHtmlMessage(referral.getStudentEmail(), subject, htmlContent, image1Path, image2Path);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }

    // referral has been acknowledged to referrer
    public String sendReferralAcknowledgementToReferrer(ReferralEntity referral) {
        String subject = "Referral Acknowledgement";
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Student Referral Acknowledgment</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        background-color: #f4f4f4;\n" +
                "        font-family: Arial, sans-serif;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f4f4f4; padding: 20px\">\n"
                +
                "      <tr>\n" +
                "        <td align=\"center\">\n" +
                "          <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #ffffff; padding: 20px\">\n"
                +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId1\" alt=\"Logo\" width=\"150\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId2\" alt=\"Referral\" width=\"100\" height=\"100\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\">\n" +
                "                <h2 style=\"font-size: 22px; font-weight: bold; color: #474647; margin: 10px 0;\">\n" +
                "                  Referral Acknowledgment\n" +
                "                </h2>\n" +
                "                <hr style=\"border: 1px solid #8a252c; width: 30%; margin: 10px auto;\" />\n" +
                "                <p style=\"font-size: 14px; color: #474647; line-height: 1.5; margin: 20px;\">\n" +
                "                  <strong>Dear " + referral.getReferrer().getFirstName() + " "
                + referral.getReferrer().getLastName() + ",</strong><br /><br />\n" +
                "                  This is to inform you that <strong>{{studentName}}</strong> has acknowledged the referral for the counseling session.\n"
                +
                "                </p>\n" +
                "                <p style=\"font-size: 12px; color: #474647; margin-top: 10px\">\n" +
                "                  Thank you for your support in this process!\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        String image1Path = "static/images/logowords.png";
        String image2Path = "static/images/referral-user.png";

        try {
            emailService.sendHtmlMessage(referral.getReferrer().getInstitutionalEmail(), subject, htmlContent,
                    image1Path, image2Path);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }

    // referral has been acknowledged to teacher
    public String sendReferralAcknowledgementToTeacher(ReferralEntity referral) {
        String subject = "Referral Acknowledgement";
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Student Referral Acknowledgment</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        background-color: #f4f4f4;\n" +
                "        font-family: Arial, sans-serif;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f4f4f4; padding: 20px\">\n"
                +
                "      <tr>\n" +
                "        <td align=\"center\">\n" +
                "          <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #ffffff; padding: 20px\">\n"
                +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId1\" alt=\"Logo\" width=\"150\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId2\" alt=\"Referral\" width=\"100\" height=\"100\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\">\n" +
                "                <h2 style=\"font-size: 22px; font-weight: bold; color: #474647; margin: 10px 0;\">\n" +
                "                  Referral Acknowledgment\n" +
                "                </h2>\n" +
                "                <hr style=\"border: 1px solid #8a252c; width: 30%; margin: 10px auto;\" />\n" +
                "                <p style=\"font-size: 14px; color: #474647; line-height: 1.5; margin: 20px;\">\n" +
                "                  <strong>Dear " + referral.getReferrer().getFirstName() + " "
                + referral.getReferrer().getLastName() + ",</strong><br /><br />\n" +
                "                  This is to inform you that <strong>{{studentName}}</strong> has acknowledged the referral for the counseling session.\n"
                +
                "                </p>\n" +
                "                <p style=\"font-size: 12px; color: #474647; margin-top: 10px\">\n" +
                "                  Thank you for your support in this process!\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        String image1Path = "static/images/logowords.png";
        String image2Path = "static/images/referral-user.png";

        try {
            emailService.sendHtmlMessage(referral.getCounselor().getInstitutionalEmail(), subject, htmlContent,
                    image1Path, image2Path);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }

    // appointment created
    public String studentCreateAppointment(AppointmentEntity appointment) {
        String subject = "Appointment Created";
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Appointment Confirmation</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        background-color: #f4f4f4;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f4f4f4; padding: 20px;\">\n"
                +
                "      <tr>\n" +
                "        <td align=\"center\">\n" +
                "          <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #ffffff; padding: 20px;\">\n"
                +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                <img src=\"cid:imageId1\" alt=\"Logo\" width=\"150\" style=\"display: block; margin: 0 auto;\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                <img src=\"cid:imageId2\" alt=\"Appointment\" width=\"100\" height=\"100\" style=\"display: block; margin: 0 auto;\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\">\n" +
                "                <h2 style=\"font-size: 22px; font-weight: bold; color: #474647; margin: 10px 0;\">Appointment Confirmation</h2>\n"
                +
                "                <hr style=\"border: 1px solid #8a252c; width: 30%; margin: 10px auto;\" />\n" +
                "                <p style=\"font-size: 14px; color: #474647; line-height: 1.5; margin: 20px;\">\n" +
                "                  <strong>Dear " + appointment.getStudent().getFirstName() + " "
                + appointment.getStudent().getLastName() + ",</strong><br /><br />\n" +
                "                  Your appointment has been successfully booked. Please review the details below:\n" +
                "                </p>\n" +
                "                <table align=\"center\" width=\"80%\" cellpadding=\"5\" cellspacing=\"0\" style=\"border: 1px solid #ddd; border-radius: 5px; margin-bottom: 20px;\">\n"
                +
                "                  <tr>\n" +
                "                    <td><strong>Date:</strong></td>\n" +
                "                    <td>" + appointment.getAppointmentDate() + "</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td><strong>Time:</strong></td>\n" +
                "                    <td>" + appointment.getAppointmentStartTime() + "</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td><strong>Counselor:</strong></td>\n" +
                "                    <td>" + appointment.getCounselor().getFirstName() + " "
                + appointment.getCounselor().getLastName() + "</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td><strong>Location:</strong></td>\n" +
                "                    <td>Guidance Office, RTL Building</td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "                <a href=\"" + base_link
                + "student/appointment\" style=\"background-color: #8a252c; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;\">View Appointment</a>\n"
                +
                "                <p style=\"font-size: 12px; color: #474647; margin-top: 10px;\">\n" +
                "                  If the button isn't clickable, please use the following link instead: <a href=\""
                + base_link + "student/appointment\" style=\"color: #8a252c;\">" + base_link
                + "student/appointment</a>\n" +
                "                </p>\n" +
                "                <p style=\"font-size: 12px; color: #474647; margin: 30px 0 10px; text-align: center;\">\n"
                +
                "                  Thank you for choosing WellTalk. We look forward to serving you!\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        String image1Path = "static/images/logowords.png";
        String image2Path = "static/images/appointment.png";

        try {
            emailService.sendHtmlMessage(appointment.getStudent().getInstitutionalEmail(), subject, htmlContent,
                    image1Path, image2Path);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }

    public String referralCreateAccountAppointment(AppointmentEntity appointment) {
        String subject = "Account and Appointment Created";
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Appointment Confirmation & Account Created</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        background-color: #f4f4f4;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f4f4f4; padding: 20px\">\n"
                +
                "      <tr>\n" +
                "        <td align=\"center\">\n" +
                "          <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #ffffff; padding: 20px\">\n"
                +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId1\" alt=\"Logo\" width=\"150\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId2\" alt=\"Appointment\" width=\"100\" height=\"100\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\">\n" +
                "                <h2 style=\"font-size: 22px; font-weight: bold; color: #474647; margin: 10px 0;\">Appointment Confirmation</h2>\n"
                +
                "                <hr style=\"border: 1px solid #8a252c; width: 30%; margin: 10px auto;\" />\n" +
                "                <p style=\"font-size: 14px; color: #474647; line-height: 1.5; margin: 20px;\">\n" +
                "                  <strong>Dear " + appointment.getStudent().getFirstName() + " "
                + appointment.getStudent().getLastName() + ",</strong><br /><br />\n" +
                "                  Your appointment has been successfully booked, and a new account has been created for you. Please review the details below:\n"
                +
                "                </p>\n" +
                "                <h3 style=\"font-size: 18px; color: #474647\">Account Details:</h3>\n" +
                "                <table align=\"center\" width=\"80%\" cellpadding=\"5\" cellspacing=\"0\" style=\"border: 1px solid #ddd; border-radius: 5px; margin-bottom: 20px;\">\n"
                +
                "                  <tr>\n" +
                "                    <td><strong>Email:</strong></td>\n" +
                "                    <td>" + appointment.getStudent().getInstitutionalEmail() + "</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td><strong>Password:</strong></td>\n" +
                "                    <td>12345678</td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "                <h3 style=\"font-size: 18px; color: #474647\">Appointment Details:</h3>\n" +
                "                <table align=\"center\" width=\"80%\" cellpadding=\"5\" cellspacing=\"0\" style=\"border: 1px solid #ddd; border-radius: 5px; margin-bottom: 20px;\">\n"
                +
                "                  <tr>\n" +
                "                    <td><strong>Date:</strong></td>\n" +
                "                    <td>" + appointment.getAppointmentDate() + "</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td><strong>Time:</strong></td>\n" +
                "                    <td>" + appointment.getAppointmentStartTime() + "</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td><strong>Counselor:</strong></td>\n" +
                "                    <td>" + appointment.getCounselor().getFirstName() + " "
                + appointment.getCounselor().getLastName() + "</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td><strong>Location:</strong></td>\n" +
                "                    <td>Guidance Office, RTL Building</td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "                <a href=\"" + base_link
                + "student/appointment\" style=\"background-color: #8a252c; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;\">View Appointment</a>\n"
                +
                "                <p style=\"font-size: 12px; color: #474647; margin-top: 10px\">\n" +
                "                  If the button isn't clickable, please use the following link instead:\n" +
                "                  <a href=\"" + base_link + "student/appointment\" style=\"color: #8a252c\">"
                + base_link + "student/appointment</a>\n"
                +
                "                </p>\n" +
                "                <p style=\"font-size: 12px; color: #474647; margin: 30px 0 10px; text-align: center;\">\n"
                +
                "                  Thank you for choosing WellTalk. We look forward to serving you!\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        String image1Path = "static/images/logowords.png";
        String image2Path = "static/images/appointment.png";

        try {
            emailService.sendHtmlMessage(appointment.getStudent().getInstitutionalEmail(), subject, htmlContent,
                    image1Path, image2Path);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }

    }

    public String updateToReferrer(String subject, String message, ReferralEntity referral) {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Referral Status Update</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        background-color: #f4f4f4;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f4f4f4; padding: 20px\">\n"
                +
                "      <tr>\n" +
                "        <td align=\"center\">\n" +
                "          <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #ffffff; padding: 20px\">\n"
                +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId1\" alt=\"Logo\" width=\"150\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId2\" alt=\"Referral\" width=\"100\" height=\"100\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\">\n" +
                "                <h2 style=\"font-size: 22px; font-weight: bold; color: #474647; margin: 10px 0;\">Referral Status Update</h2>\n"
                +
                "                <hr style=\"border: 1px solid #8a252c; width: 30%; margin: 10px auto;\" />\n" +
                "                <p style=\"font-size: 14px; color: #474647; line-height: 1.5; margin: 20px;\">\n" +
                "                  <strong>Dear " + referral.getReferrer().getFirstName() + " "
                + referral.getReferrer().getLastName() + ",</strong><br /><br />\n" +
                "                  You referred <strong>" + referral.getStudent().getFirstName() + " "
                + referral.getStudent().getLastName() + "</strong>, " + referral.getRelationship()
                + ". We wanted to let you know that:\n"
                +
                "                </p>\n" +
                "                <p style=\"font-size: 14px; color: #474647; line-height: 1.5; margin: 20px;\">\n"
                + message +
                "                 \n"
                +
                "                </p>\n" +
                "                <p style=\"font-size: 14px; color: #474647; margin: 20px;\">\n" +
                "                  Thank you for referring " + referral.getStudent().getFirstName() + " "
                + referral.getStudent().getLastName() + ". If you have any questions, feel free to reach out!\n"
                +
                "                </p>\n" +
                "                <p style=\"font-size: 12px; color: #474647; margin: 30px 0 10px; text-align: center;\">\n"
                +
                "                  Best regards,<br />\n" +
                "                  The WellTalk Team\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        String image1Path = "static/images/logowords.png";
        String image2Path = "static/images/referral-user.png";

        try {
            emailService.sendHtmlMessage(referral.getReferrer().getInstitutionalEmail(), subject, htmlContent,
                    image1Path, image2Path);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }

    }

    public String sendForgotPasswordEmail(String resetLink, String recipientEmail) {
        String subject = "Forgot Password";
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Password Reset</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        font-family: Arial, sans-serif;\n" +
                "        background-color: #f4f4f4;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f4f4f4; padding: 20px\">\n"
                +
                "      <tr>\n" +
                "        <td align=\"center\">\n" +
                "          <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #ffffff; padding: 20px\">\n"
                +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId1\" alt=\"Logo\" width=\"150\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px\">\n" +
                "                <img src=\"cid:imageId2\" alt=\"Forgot Password\" width=\"100\" height=\"100\" style=\"display: block; margin: 0 auto\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\">\n" +
                "                <p style=\"font-size: 25px; font-weight: bold; color: #474647; margin: 10px 0;\">\n" +
                "                  Forgot Your Password?\n" +
                "                </p>\n" +
                "                <hr style=\"border: 1px solid #8a252c; width: 30%; margin: 10px auto;\" />\n" +
                "                <p style=\"font-size: 12px; color: #474647; line-height: 1.5; margin: 20px;\">\n" +
                "                  No worries! We’ve got you covered. You requested to reset your password for your WellTalk account.\n"
                +
                "                  <br /><br />\n" +
                "                  Click the button below to reset your password. If you didn’t request a password reset, you can safely ignore this email.\n"
                +
                "                </p>\n" +
                "                <a href=\"" + resetLink
                + "\" style=\"background-color: #8a252c; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;\">\n"
                +
                "                  Reset Password\n" +
                "                </a>\n" +
                "                <p style=\"font-size: 12px; color: #474647; margin-top: 10px\">\n" +
                "                  If the button isn't clickable, please use the following link instead:\n" +
                "                  <a href=\"" + resetLink + "\" style=\"color: #8a252c\">" + resetLink + "</a>\n" +
                "                </p>\n" +
                "                <p style=\"font-size: 12px; color: #474647; margin-top: 20px\">\n" +
                "                  If you have any issues, feel free to contact our support team at\n" +
                "                  <a href=\"mailto:support@welltalk.com\" style=\"color: #8a252c\">cit.welltalk@gmail.com</a>.\n"
                +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        String image1Path = "static/images/logowords.png";
        String image2Path = "static/images/password.png";

        try {
            emailService.sendHtmlMessage(recipientEmail, subject, htmlContent, image1Path, image2Path);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }

    public String studentUpdateAppointment(AppointmentEntity appointment) {
        String subject = "Appointment Updated";
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Appointment Update</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        background-color: #f4f4f4;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f4f4f4; padding: 20px;\">\n"
                +
                "      <tr>\n" +
                "        <td align=\"center\">\n" +
                "          <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #ffffff; padding: 20px;\">\n"
                +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                <img src=\"cid:imageId1\" alt=\"Logo\" width=\"150\" style=\"display: block; margin: 0 auto;\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding: 20px;\">\n" +
                "                <img src=\"cid:imageId2\" alt=\"Appointment Update\" width=\"100\" height=\"100\" style=\"display: block; margin: 0 auto;\" />\n"
                +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td align=\"center\">\n" +
                "                <h2 style=\"font-size: 22px; font-weight: bold; color: #474647; margin: 10px 0;\">Appointment Update</h2>\n"
                +
                "                <hr style=\"border: 1px solid #8a252c; width: 30%; margin: 10px auto;\" />\n" +
                "                <p style=\"font-size: 14px; color: #474647; line-height: 1.5; margin: 20px;\">\n" +
                "                  <strong>Dear " + appointment.getStudent().getFirstName() + " "
                + appointment.getStudent().getLastName() + ",</strong><br /><br />\n" +
                "                  Your appointment has been updated. Please find the revised details below:\n" +
                "                </p>\n" +
                "                <table align=\"center\" width=\"80%\" cellpadding=\"5\" cellspacing=\"0\" style=\"border: 1px solid #ddd; border-radius: 5px; margin-bottom: 20px;\">\n"
                +
                "                  <tr>\n" +
                "                    <td><strong>New Date:</strong></td>\n" +
                "                    <td>" + appointment.getAppointmentDate() + "</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td><strong>New Time:</strong></td>\n" +
                "                    <td>" + appointment.getAppointmentStartTime() + "</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td><strong>Counselor:</strong></td>\n" +
                "                    <td>" + appointment.getCounselor().getFirstName() + " "
                + appointment.getCounselor().getLastName() + "</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td><strong>Location:</strong></td>\n" +
                "                    <td>Guidance Office, RTL Building</td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "                <a href=\"" + base_link
                + "student/appointment\" style=\"background-color: #8a252c; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;\">View Appointment</a>\n"
                +
                "                <p style=\"font-size: 12px; color: #474647; margin-top: 10px;\">\n" +
                "                  If the button isn't clickable, please use the following link instead: <a href=\""
                + base_link + "student/appointment\" style=\"color: #8a252c;\">" + base_link
                + "student/appointment</a>\n" +
                "                </p>\n" +
                "                <p style=\"font-size: 12px; color: #474647; margin: 30px 0 10px; text-align: center;\">\n"
                +
                "                  Thank you for choosing WellTalk. We apologize for any inconvenience caused by this change.\n"
                +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        String image1Path = "static/images/logowords.png";
        String image2Path = "static/images/appointment.png";

        try {
            emailService.sendHtmlMessage(appointment.getStudent().getInstitutionalEmail(), subject, htmlContent,
                    image1Path, image2Path);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }

}
