package idv.tfp10102.wayne.ch22_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import idv.tfp10102.wayne.R;


// scroll物件本身，內含資料及所使用的內容樣式(ViewHolder)
public class Ch22_1_Adapter extends RecyclerView.Adapter<Ch22_1_Adapter.Ch22_1_ViewHolder> {
    Context context;
    List<Book> list;

    public Ch22_1_Adapter(Context context, List<Book> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Ch22_1_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 利用Inflater把預先做好的layout(cardView)做成view
        View itemView = LayoutInflater.from(context).inflate(R.layout.ch22_1_item_view, parent, false);
        // 把itemView做成ViewHolder
        return new Ch22_1_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Ch22_1_ViewHolder holder, int position) {
        // 根據position把對應的資料存入到ViewHolder中
        Book book = list.get(position);

        holder.getImageView().setImageResource(book.getImageId());
        holder.getTextView().setText(book.getTitle());

        // 點擊選項後跳出排名及書名
        holder.getImageView().setOnClickListener((view) -> {
            Toast.makeText(context, (position + 1) + " : " + book.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    // 相當於內容物件，其欄位對應CardView的內容
    static class Ch22_1_ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        // RecyclerView.ViewHolder所需要的建構式，itemView為參數
        public Ch22_1_ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ch22_1_bookImg);
            textView = itemView.findViewById(R.id.ch22_1_bookText);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }
}
