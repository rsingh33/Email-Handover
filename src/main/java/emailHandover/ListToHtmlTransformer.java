package emailHandover;

/**+
 * Transforms the message into email format for handover
 */
public class ListToHtmlTransformer {


    public String compose(java.util.Collection<HandoverRow> handover) {
        StringBuilder email = new StringBuilder();
        email.append("<html>");
        email.append("<head>\n");
        email.append("<meta charset='utf-8'>\n");
        email.append("<title>Daily Email Handover</title>\n");
        email.append("<style>\n" +
                "table, th, td {\n" +
                "    border: 1px solid black;\n" +
                "}\n" +
                "</style>");
        email.append("</head>\n\n");
        email.append("<body>");
        email.append("<table style='border:2px solid black;width:100%'>");
        email.append("<tr ><td bgcolor=\"#A8CBFD\" align=\"center\" colspan=\"6\" style='font-weight:bold;font-size:25px'>Open Issues</td></tr>");
        email.append("<tr bgcolor=\"#dfe8f0\">");
      //  email.append("<th>ID</th>");
        email.append("<th>Reporter</th>");
        email.append("<th>Email Subject</th>");
        email.append("<th>Tracking</th>");
        email.append("<th>Comments</th>");
        email.append("<th>Last Modified</th>");
        email.append("</tr>");


        for (HandoverRow row : handover) {

            email.append("<tr bgcolor=\"#f1f9fc\">");
         /*   email.append("<td>");
            email.append(row.getId());
            email.append("</td>");
*/
            email.append("<td>");
            email.append(row.getReportedBy());
            email.append("</td>");

            email.append("<td>");
            email.append(row.getEmail());
            email.append("</td>");

            email.append("<td>");
            email.append(row.getTracking());
            email.append("</td>");

            email.append("<td>");
            email.append(row.getComments());
            email.append("</td>");

            email.append("<td>");
            email.append(row.getLastMod());
            email.append("</td>");
            email.append("<tr>");
        }

        email.append("</table></body></html>");
        return email.toString();

    }

}