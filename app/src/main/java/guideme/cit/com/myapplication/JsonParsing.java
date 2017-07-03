package guideme.cit.com.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boody 2 on 30/06/2017.
 */

public class JsonParsing {
    public ArrayList<Book> parseJsonObject(String buffer) {
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(buffer);
            int j = jsonObject.getInt("totalItems");
            if (j != 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                JSONObject jsonObjectBook;
                Book book;
                for (int i = 0; i < jsonArray.length(); i++) {
                    book = new Book();
                    jsonObjectBook = jsonArray.getJSONObject(i);
                    book.setTitle(jsonObjectBook.getJSONObject("volumeInfo").getString("title"));
                    //book.setSubtitle(jsonObjectBook.getString("subtitle"));

                    JSONArray authorsArray = jsonObjectBook.getJSONObject("volumeInfo").optJSONArray("authors");
                    if(authorsArray != null) {
                        for (int m = 0; m < authorsArray.length(); m++) {
                            book.setAuthors(authorsArray.getString(m));
                        }
                    }
                    //book.setDescription(jsonObjectBook.getString("description"));
                    bookList.add(book);
                }
                return bookList;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
