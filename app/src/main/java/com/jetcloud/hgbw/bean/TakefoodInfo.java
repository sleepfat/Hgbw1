package com.jetcloud.hgbw.bean;

import java.util.List;

/***
 * Created by Cqing on 2016/12/27.
 */

public class TakeFoodInfo{

    /**
     * status : success
     * orders : [{"number":"14850724841750824",
     * "food_info":{"foods":[{"food_pic":"/static/food/ef12639c-d2f0-11e6-b743-6807159ba7ea.jpg","food_price":"0.15",
     * "id":"12","num":"1","food_pay_way":"10","food_name":"麻辣香锅"}],"mechine_number":"1234567890",
     * "foodd_out":[{"num":"1","pos":3}]},"create_time":"2017-01-22 16:08:04","pay_type":"10","id":63,"cost_real":0
     * .15,"user_phone":"13340902246","state":"1","cost_total":0.15}]
     */

    private String status;
    private List<OrdersBean> orders;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class OrdersBean {
        /**
         * number : 14850724841750824
         * food_info : {"foods":[{"food_pic":"/static/food/ef12639c-d2f0-11e6-b743-6807159ba7ea.jpg","food_price":"0
         * .15","id":"12","num":"1","food_pay_way":"10","food_name":"麻辣香锅"}],"mechine_number":"1234567890",
         * "foodd_out":[{"num":"1","pos":3}]}
         * create_time : 2017-01-22 16:08:04
         * pay_type : 10
         * id : 63
         * cost_real : 0.15
         * user_phone : 13340902246
         * state : 1
         * cost_total : 0.15
         */

        private String number;
        private FoodInfoBean food_info;
        private String create_time;
        private String pay_type;
        private int id;
        private double cost_real;
        private String user_phone;
        private String state;
        private double cost_total;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public FoodInfoBean getFood_info() {
            return food_info;
        }

        public void setFood_info(FoodInfoBean food_info) {
            this.food_info = food_info;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getCost_real() {
            return cost_real;
        }

        public void setCost_real(double cost_real) {
            this.cost_real = cost_real;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public double getCost_total() {
            return cost_total;
        }

        public void setCost_total(double cost_total) {
            this.cost_total = cost_total;
        }

        public static class FoodInfoBean {
            /**
             * foods : [{"food_pic":"/static/food/ef12639c-d2f0-11e6-b743-6807159ba7ea.jpg","food_price":"0.15",
             * "id":"12","num":"1","food_pay_way":"10","food_name":"麻辣香锅"}]
             * mechine_number : 1234567890
             * foodd_out : [{"num":"1","pos":3}]
             */

            private String mechine_number;
            private String mechine_name;
            private List<FoodsBean> foods;
            private List<FooddOutBean> foodd_out;

            public String getMechine_number() {
                return mechine_number;
            }

            public void setMechine_number(String mechine_number) {
                this.mechine_number = mechine_number;
            }

            public String getMechine_name() {
                return mechine_name;
            }

            public void setMechine_name(String mechine_name) {
                this.mechine_name = mechine_name;
            }

            public List<FoodsBean> getFoods() {
                return foods;
            }

            public void setFoods(List<FoodsBean> foods) {
                this.foods = foods;
            }

            public List<FooddOutBean> getFoodd_out() {
                return foodd_out;
            }

            public void setFoodd_out(List<FooddOutBean> foodd_out) {
                this.foodd_out = foodd_out;
            }

            public static class FoodsBean {
                /**
                 * food_pic : /static/food/ef12639c-d2f0-11e6-b743-6807159ba7ea.jpg
                 * food_price : 0.15
                 * id : 12
                 * num : 1
                 * food_pay_way : 10
                 * food_name : 麻辣香锅
                 */

                private String food_pic;
                private String food_price_vr9;
                private String food_price_rmb;
                private String id;
                private String num;
                private String food_pay_way;
                private String food_name;
                private String food_des;

                public String getFood_pic() {
                    return food_pic;
                }

                public void setFood_pic(String food_pic) {
                    this.food_pic = food_pic;
                }

                public String getFood_price_vr9() {
                    return food_price_vr9;
                }

                public void setFood_price_vr9(String food_price_vr9) {
                    this.food_price_vr9 = food_price_vr9;
                }

                public String getFood_price_rmb() {
                    return food_price_rmb;
                }

                public void setFood_price_rmb(String food_price_rmb) {
                    this.food_price_rmb = food_price_rmb;
                }

                public String getFood_des() {
                    return food_des;
                }

                public void setFood_des(String food_des) {
                    this.food_des = food_des;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getFood_pay_way() {
                    return food_pay_way;
                }

                public void setFood_pay_way(String food_pay_way) {
                    this.food_pay_way = food_pay_way;
                }

                public String getFood_name() {
                    return food_name;
                }

                public void setFood_name(String food_name) {
                    this.food_name = food_name;
                }
            }

        }
    }
}
