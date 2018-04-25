package com.fragment.navigation.jabed.apraisesend;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;


import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CarouselPreviewActivity extends AppCompatActivity {

    public static int INVALID_POSITION = -1;
    Button btnRandomiser;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_carousel_preview);

        //final ActivityCarouselPreviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_carousel_preview);
        Toolbar tbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        btnRandomiser = (Button) findViewById(R.id.btnRandomiser);

        final HorizontalAdaptar adapter = new HorizontalAdaptar(this);

        CustomRecyclerView rh = (CustomRecyclerView) findViewById(R.id.list_horizontal);

        // create layout manager with needed params: vertical, cycle
        initRecyclerView(rh, new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false), adapter);


    }

    private void initRecyclerView(final RecyclerView recyclerView, final CarouselLayoutManager layoutManager, final HorizontalAdaptar adapter) {
        // enable zoom effect. this line can be customized
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        recyclerView.setLayoutManager(layoutManager);
        // we expect only fixed sized item for now
        recyclerView.setHasFixedSize(true);
        // sample adapter with random data
        recyclerView.setAdapter(adapter);
        // enable center post scrolling
        recyclerView.addOnScrollListener(new CenterScrollListener());

        btnRandomiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//recyclerView.scrollToPosition(6);
                final Random mRandom = new Random();
                final int min = 1;
                final int max = 10;
                final int random = mRandom.nextInt((max - min) + 1) + min;
                Log.e("random", "" + random);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        //recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                        final int speedScroll = 170;
                        final Handler handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            int count = 0;
                            boolean flag = true;

                            @Override
                            public void run() {
                                if (count < adapter.getItemCount()) {
                                    if (count == adapter.getItemCount() - 1) {
                                        flag = false;
                                    } else if (count == 0) {
                                        flag = true;
                                    }
                                    if (flag) count++;
                                    else count--;

                                    recyclerView.smoothScrollToPosition(random);
                                    handler.postDelayed(this, speedScroll);
                                }
                            }
                        };

                        handler.postDelayed(runnable, speedScroll);
                    }
                });
            }
        });

        layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {

            @Override
            public void onCenterItemChanged(final int adapterPosition) {
              /*  if (INVALID_POSITION != adapterPosition) {
                    final int value = adapter.mPosition[adapterPosition];
                    adapter.mPosition[adapterPosition] = (value % 10) + (value / 10 + 1) * 100;
                    adapter.notifyItemChanged(adapterPosition);
                }*/
            }
        });
    }


    private static final class HorizontalAdaptar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @SuppressWarnings("UnsecureRandomNumberGeneration")
        private final Random mRandom = new Random();
        private final int[] mColors;
        private final int[] mPosition;
        private Context context;
        private final int[] image = {
                R.drawable.baby,
                R.drawable.smile,
                R.drawable.baby,
                R.drawable.smile,
                R.drawable.baby,
                R.drawable.smile,
                R.drawable.baby,
                R.drawable.smile,
                R.drawable.baby,
                R.drawable.smile,
                R.drawable.smile,
                R.drawable.baby,
                R.drawable.smile,
                R.drawable.baby,
                R.drawable.smile,
                R.drawable.baby,
                R.drawable.smile,

        };
        private final String[] title = {
                "Prince",
                "John",
                "Jin Yean",
                "Chin Sze Yen",
                "Prince",
                "John",
                "Jin Yean",
                "Chin Sze Yen",
                "Prince",
                "John",
                "Jin Yean",
                "Chin Sze Yen",
                "Chin Sze Yen",
                "Prince",
                "John",
                "Jin Yean",
                "Chin Sze Yen",

        };


        private int mItemsCount = 10;
        LayoutInflater inflater;

        HorizontalAdaptar(Context context) {
            this.context = context;

            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mColors = new int[10];
            mPosition = new int[10];

            for (int i = 0; 10 > i; ++i) {
                //noinspection MagicNumber
                mColors[i] = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
                mPosition[i] = i;

            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_view, null);
            RecyclerView.ViewHolder holder = new RowNewsViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((RowNewsViewHolder) holder).cItem1.setText(title[position]);

            ((RowNewsViewHolder) holder).pp.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), image[position], null));


            // ((RowNewsViewHolder) holder).pp.setBackgroundResource(R.drawable.border_box_red_circle);
            //  ((RowNewsViewHolder) holder).cItem2.setText(String.valueOf(mPosition[position]));

            //holder.itemView.setBackgroundColor(mColors[position]);
        }

        @Override
        public int getItemCount() {
            return mItemsCount;
        }
    }

    public static class RowNewsViewHolder extends RecyclerView.ViewHolder {
        TextView cItem1;
        CircleImageView pp;


        public RowNewsViewHolder(View itemView) {
            super(itemView);

            cItem1 = (TextView) itemView.findViewById(R.id.c_item_1);
            pp = (CircleImageView) itemView.findViewById(R.id.profilePicture);

        }
    }


}