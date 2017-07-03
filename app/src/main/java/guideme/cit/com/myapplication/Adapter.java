package guideme.cit.com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by boody 2 on 30/06/2017.
 */

public class Adapter extends ArrayAdapter {
    private List<Book> bookList;
    private Context context;

    public Adapter(Context context, int resource, List<Book> bookList) {
        super(context, resource, bookList);
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Book book = bookList.get(position);
        viewHolder.title.setText("Title: " + book.getTitle().toString());
        if (bookList.get(position).getAuthors() != null) {
            viewHolder.author.setText("Author: " + book.getAuthors());
        } else {
            viewHolder.author.setText("No Author");
        }
        return convertView;
    }
}

