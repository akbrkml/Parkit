package com.example.isyandra.parkit.model;

import java.util.List;

/**
 * Created by Arief11 on 5/7/2017.
 */

public class PromoData
{
    public int page;
    public List<Result> object_name;
    public int total_results;
    public int total_pages;
    public class Result
    {
        public String gambar;
        public boolean adult;
        public String tanggal;
        public String release_date;
        public List<Integer> genre_ids;
        public int id;
        public String judul;
        public String original_language;
        public String title;
        public String backdrop_path;
        public double popularity;
        public int vote_count;
        public boolean video;
        public double vote_average;
    }

}
