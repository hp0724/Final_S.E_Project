package gachon.mpclass.final_mobile_project.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import gachon.mpclass.final_mobile_project.Bookmark.BookmarkActivity;
import gachon.mpclass.final_mobile_project.Manager.DBManager;
import gachon.mpclass.final_mobile_project.R;
import gachon.mpclass.final_mobile_project.Review.ListReviewActivity;

import gachon.mpclass.final_mobile_project.Review.ReviewDto;

public class ReservationActivity extends AppCompatActivity {
    TextView etTitle;
    TextView etTitle2;
    TextView etTitle3;
    TextView etTitle4;
    TextView etContent;

    DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        etTitle = findViewById(R.id.reservation_detailTitle);
        etTitle2 = findViewById(R.id.reservation_detailPrice);
        etTitle3 = findViewById(R.id.reservation_detailAddr);
        etTitle4 = findViewById(R.id.reservation_detailURL);

        etContent = findViewById(R.id.et_write_review);

        manager = new DBManager(this);

        //인텐트에서 꺼낸 값이 널이 아닐 경우에만 공연 제목 자동으로 넣어주고 아니면 직접 입력하게 하기
        String str = getIntent().getStringExtra("title");
        if (str != null) {
            etTitle.setText(str);
        }
        String str2 = getIntent().getStringExtra("price");
        if (str != null) {
            etTitle2.setText(str2);
        }
        String str3 = getIntent().getStringExtra("PlaceUrl");
        if (str != null) {
            etTitle3.setText(str3);
        }
        String str4 = getIntent().getStringExtra("TicketLink");
        if (str != null) {
            etTitle4.setText(str4);
        }


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_write_review:
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();

                if (!title.isEmpty() && !content.isEmpty()) {
                    ReviewDto dto = new ReviewDto();
                    dto.setTitle(title);
                    dto.setContent(content);

                    boolean result = manager.addReview(dto);
                    if (result) {
                        startActivity(new Intent(this, ListReviewActivity.class));
                    } else {
                        Toast.makeText(this, "리뷰 추가 실패", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (title.isEmpty()) {
                        Toast.makeText(this, "공연 제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                    } else if (content.isEmpty()) {
                        Toast.makeText(this, "리뷰를 작성하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_write_cancel:
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.item_bookmark:
                intent = new Intent(this, BookmarkActivity.class);
                break;
            case R.id.item_review:
                intent = new Intent(this, ListReviewActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);

        return true;
    }
}