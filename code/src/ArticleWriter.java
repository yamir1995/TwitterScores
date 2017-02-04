import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Class ArticleWriter
 * 
 * The only purpose of this class is to actually get the articles and write them to a Text File called articles.txt.
 * 
 * @author Rohan
 *
 */



public class ArticleWriter
{
	// Query the guardian the and get the articles related to football in the recent weeks
	public static void main(String[] args)
	{
		URLGetter url = new URLGetter(
				"http://content.guardianapis.com/search?"
						+ "section=football"
						+ "&from-date=2015-04-15"
						+ "&to-date=2015-05-04"
						+ "&order-by=relevance"
						+ "&show-fields=body"
						+ "&page-size=50"
						+ "&production-office=uk"
						+ "&api-key=x3b3yedsukpd6rmty59vwcm7");
		
		try {
			BufferedWriter toWrite = new BufferedWriter(new FileWriter("articles.txt"));
			ArrayList<MyDocument> result = new ArrayList<MyDocument>();
			org.json.JSONObject response = new JSONObject( url.getContents().get(0));
			JSONObject response2 = response.getJSONObject("response");
			JSONArray results = response2.getJSONArray("results");
			System.out.println(results);
			// Obtain the article and parse only the title, date and content. Write it into a file.
			for(int i = 0; i < results.length(); i++)
			{
				JSONObject MyDocument = results.getJSONObject(i);
				try {
					JSONObject content = MyDocument.getJSONObject("fields");
					String fullDate = MyDocument.getString("webPublicationDate");
					String title = MyDocument.getString("webTitle");
					String date = fullDate.split("T")[0].trim();
					String body = content.getString("body");
					org.jsoup.nodes.Document d = Jsoup.parseBodyFragment(body);
					toWrite.write("Title:" + title + "\n");
					toWrite.write("Date:" + date + "\n");
					toWrite.write("Content:" + d.text() + "\n");
				} catch (JSONException w)
				{
					continue;
				}
				
			}
			
			
			toWrite.close();	
		} catch (JSONException | IOException e) {			
			
		} 
		
		
	}

}
