package guideme.cit.com.myapplication;

import android.view.View;
import android.widget.TextView;

/**
 * Created by boody 2 on 02/07/2017.
 */

public class ViewHolder {

    public TextView author;
    public TextView title;

    public ViewHolder(View view) {
        this.author = (TextView) view.findViewById(R.id.author);
        this.title = (TextView) view.findViewById(R.id.bookTitle);
    }
}
