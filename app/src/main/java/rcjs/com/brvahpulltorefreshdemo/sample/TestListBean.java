package rcjs.com.brvahpulltorefreshdemo.sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rcjs on 2017/12/8.
 * Description:bean实体类
 */


public class TestListBean {
    private int count;
    private int status;
    private String statusDesc;
    private List<ListBean> list = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }


    public static class ListBean implements Serializable {

        private String name;
        private String address;
        private String tel;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}
