package com.example.brianb.demo1;


/**
 * Created by BrianB on 24-Jun-17.
 */

public class MyImage {
        private String title, description, path;
        long price, pId;
        //private SimpleDateFormat df = new SimpleDateFormat("MMMM d, yy  h:mm");

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

    public MyImage(String title, String description, long price, String path) {
            this.title = title;
            this.description = description;
            this.price = price;
            this.path = path;
        }

        public MyImage() {
        }

        public String getTitle() { return title; }

        public String getDescription() { return description; }

        public void setTitle(String title) { this.title = title; }

        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Sets new path.
         *
         * @param path New value of path.
         */
        public void setPath(String path) { this.path = path; }

        /**
         * Gets path.
         *
         * @return Value of path.
         */
        public String getPath() { return path; }


        @Override public String toString() {
            return  title +  "\n" + description;
        }
}