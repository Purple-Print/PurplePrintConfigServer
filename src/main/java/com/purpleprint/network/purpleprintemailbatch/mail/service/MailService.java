package com.purpleprint.network.purpleprintemailbatch.mail.service;

import com.purpleprint.network.purpleprintemailbatch.heart.command.application.dto.RecipientDTO;
import com.purpleprint.network.purpleprintemailbatch.job.dto.MailDTO;
import com.purpleprint.network.purpleprintemailbatch.user.command.application.dto.PlayFriendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * <pre>
 * Class : MailService
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-25       전현정           최초 생성
 * </pre>
 *
 * @author 전현정(최초 작성자)
 * @version 1(클래스 버전)
 * @see
 */
@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String email;
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendMail(MailDTO mail) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(email);
        helper.setTo(mail.getParentsMail());

        String sendText = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <title></title>\n" +
                "\n" +
                "\n" +
                "</head>\n" +
                "<body style=\"margin:0;padding:0;word-spacing:normal;background-color:#939297;\">\n" +
                "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#939297;\">\n" +
                "    <table role=\"presentation\" style=\"width:100%;border:none;border-spacing:0;\">\n" +
                "      <tr>\n" +
                "        <td align=\"center\" style=\"padding:0;\">\n" +
                "\n" +
                "          <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                "            <tr>\n" +
                "              <td style=\"padding:40px 30px 30px 30px;text-align:center;font-size:24px;font-weight:bold;\">\n" +
                "                <a href=\"https://ibb.co/YQfBKss\"><img src=\"\" alt=\"\" border=\"0\"></a>\n" +
                "            </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                "                <!--title-->\n" +
                "                <h1 align=\"center\" style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">필로칼로을 이용해 주셔서 감사합니다.</h1>\n" +
                "                <!--first paragraph-->\n" +
                "                <p style=\"margin:0;\"></p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "              <td align=\"center\" style=\"padding:35px 30px 11px 30px; background-color:#ffffff;border-bottom:1px solid #f0f0f5;border-color:rgba(201,201,207,.35);\">\n" +
                "\n" +
                "                <!-- <div class=\"col-sml\" style=\"display:inline-block;width:100%;max-width:145px;vertical-align:top;text-align:left;font-family:Arial,sans-serif;font-size:14px;color:#363636;\">\n" +
                "                  <img src=\"\" width=\"115\" alt=\"\" style=\"width:115px;max-width:80%;margin-bottom:20px;\">\n" +
                "                </div> -->\n" +
                "\n";


        if(!mail.getPlayFriendList().isEmpty()) {
            sendText += "       <div style='background-image:url(https://purpleprint-bucket.s3.ap-northeast-2.amazonaws.com/email/001.png); width:1000px; height: 600px; background-size: cover; display:flex;'> \n"
                        + "         <div style='width:100%; display: flex; margin:25% 12%;'> \n";

            for(PlayFriendDTO playFriendDTO : mail.getPlayFriendList()) {
                sendText +="        <div style='margin:10px;'>\n"
                        + "             <img src='" + playFriendDTO.getFriendCharacterUrl() + "' style='width:150px; height: 150px; border-radius: 50%;'/> \n"
                        + "             <div style='text-align: center; font-size: 20px; font-weight:bold;'>" + playFriendDTO.getFriendName() + "</div> \n"
                        + "             <div style='text-align: center; font-size: 20px; font-weight:bold;'>"+playFriendDTO.getFriendComment()+"</div> \n"
                        + "         </div>\n";
            }

            sendText += "   </div>\n"
                    + " </div>\n";
        }

        if(!mail.getRecipientList().isEmpty()) {
            sendText += "       <div style='background-image:url(https://purpleprint-bucket.s3.ap-northeast-2.amazonaws.com/email/002.png); width:1000px; height: 600px; background-size: cover; display:flex;'> \n"
                    + "         <div style='width:100%; display: flex; margin:25% 8%;'> \n";

            for(RecipientDTO recipientDTO : mail.getRecipientList()) {
                sendText +="        <div style='margin:10px;'>\n"
                        + "             <img src='" + recipientDTO.getFriendCharacterUrl() + "' style='width:150px; height: 150px; border-radius: 50%;'/> \n"
                        + "             <div style='text-align: center; font-size: 20px; font-weight:bold;'>" + recipientDTO.getFriendName() + "</div> \n"
                        + "         </div>\n";
            }

            sendText += "   </div>\n"
                    + " </div>\n";
        }

        sendText += "       <div style='background-image:url(https://purpleprint-bucket.s3.ap-northeast-2.amazonaws.com/email/004.png); width:1000px; height: 600px; background-size: cover; display: flex;'>\n"
                    + "         <div style='width:100%;'>\n"
                    + "             <h1 style='width:100%; margin-top:250px;' align='center'>Today's Keyword</h1> \n"
                    + "             <div style='width:100%; text-align:center;'> \n"
                    + "                 <div style='margin:20px 0; font-size:20px;'>" + mail.getPlayPlace() + "</div> \n"
                    + "             </div>\n"

                    + "            <div style='width:100%; display: flex; margin-top:50px;'>\n"
                    + "                 <div style='width:100%; text-align:center;'>\n"
                    + "                     <div style='font-size:20px; font-weight:bold;'>플레이 시간</div>\n"
                    + "                     <div>"+ mail.getPlayTime() +"</div>\n"
                    + "                 </div>\n"
                    + "              <div style='width:100%; text-align:center;'>\n"
                    + "                 <div style='font-size:20px; font-weight:bold;'>만난 친구</div>"
                    + "                 <div>"+ mail.getConCurrentFriend() +"</div>\n"
                    + "                 <div style='font-size:20px; font-weight:bold; margin-top:20px;'>아이가 하트를 준 친구 수</div>"
                    + "                 <div>"+ mail.getGiveHeartCount() +"</div>\n"
                    + "              </div>\n"
                    + "             <div style='width:100%;'>\n"
                    + "                 <div style='font-size:20px; font-weight:bold;'>COMMENT</div>\n"
                    + "                 <div>" + mail.getComment() + "</div>\n"
                    + "             </div>\n"
                    + "          </div>\n"
                    + "       </div>\n"
                    + "     </div>\n"
                    + "   </div>\n"
                    + "              </td>\n"
                    + "            </tr>\n"
                    + "\n"
                    + "\n"
                    + "            <tr>\n"
                    + "              <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#404040;color:#cccccc;\">\n"
                    + "                <p style=\"margin:0;font-size:14px;line-height:20px;\">Copyright 2022. purplerprint all rights reserved.<br></p>\n"
                    + "              </td>\n"
                    + "            </tr>\n"
                    + "          </table>\n"
                    + "\n"
                    + "        </td>\n"
                    + "      </tr>\n"
                    + "    </table>\n"
                    + "  </div>\n"
                    + "</body>\n"
                + "</html>";

        helper.setSubject("[필로칼로] "+ mail.getAnalysisDate() + " " +mail.getChildName() + "의 행동 분석 보고서 발송" );
        helper.setText("text/html", sendText);
        mailSender.send(message);

        System.out.println("메일 발송 완료");

    }
}

