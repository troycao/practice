package com.troy.practice.recoverydata;

import com.troy.practice.loganalysis.DruidJdbcUtils;
import org.springframework.util.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateCompleteData {

    public static HashMap<String,String> mIdMap = null;
    public static HashMap<String,String> tIdMap = null;
    public static HashMap<String,String> parkingMap = null;

    public static String appendFileName = "E:\\20190808\\恢复生产数据\\08\\updateMid.sql";
    public static String appendFileName1 = "E:\\20190808\\恢复生产数据\\08\\updateParkingId.sql";
    public static String appendFileName2 = "E:\\20190808\\恢复生产数据\\08\\update_qrc_payment_record.sql";
    public static String appendFileName3 = "E:\\20190808\\恢复生产数据\\08\\update_qrc_stlm_nortrans_dtlday.sql";
    public static String appendFileName4 = "E:\\20190808\\恢复生产数据\\08\\update_exittime.sql";

    static {
        mIdMap = new HashMap<String,String>();
        mIdMap.put("022","854290065138998");
        mIdMap.put("0432","854290065138999");
        mIdMap.put("0507","854290065138191");

        tIdMap = new HashMap<String,String>();
        tIdMap.put("854290065138998","13610178");
        tIdMap.put("854290065138999","13610179");
        tIdMap.put("854290065138191","13608503");

        parkingMap = new HashMap<String, String>();
        parkingMap.put("022","FuHui");
        parkingMap.put("0432","Daduhui");
        parkingMap.put("0507","Pudong Jinrong Parking");
    }

    public static void main(String[] args) {
//        updateExitEqualsPaytime();
//        completePlateNum();
//        updateTable();
//        updateQrcRecord();
//        update_qrc_stlm_nortrans_dtlday();
//        updateExittime();
        updateExMid();
    }

    /**
     * 出场时间为空的记录修改为出场时间等于入场时间
     */
    public static void updateExitEqualsPaytime(){
        String sql = "select * from parking_order_july where exittime is null ";
        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(sql);
        for (Map<String, Object> map:list) {
            String platenum = (String) map.get("PLATENUM");
            String paymentorderid = (String) map.get("PAYMENTORDERID");
            String payTime = (String) map.get("PAYTIME");
            String updateSql = "update parking_order_july set exittime=? where exittime is null " +
                    "and platenum=? and paymentorderid=? ";
            System.out.println(updateSql);
            int i = DruidJdbcUtils.exectueUpdate(updateSql,payTime,platenum,paymentorderid);
            System.out.println(i);
        }
    }

    /**
     * 完善车牌号
     */
    public static void completePlateNum(){
        String sql = "select * from parking_order_july ";
        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(sql);
        for (Map<String, Object> map:list) {
            String plateNum = (String) map.get("PLATENUM");
            String paymentorderid = (String) map.get("PAYMENTORDERID");

            String carNoSql = "select car_no from pims_parking_order where id in\n" +
                    "(select parking_order_id from pims_payment_order where id = ?)";
            List<Map<String, Object>> list1 = DruidJdbcUtils.executeQuery(carNoSql,paymentorderid);
            Map<String, Object> carNoMap = list1.get(0);
            String carNo = (String) carNoMap.get("CAR_NO");
            if (plateNum.equals(carNo.substring(1))){
                String updateSql = "update parking_order_july set platenum=? where paymentorderid=? ";
                System.out.println(updateSql);
                int i = DruidJdbcUtils.exectueUpdate(updateSql, carNo, paymentorderid);
                System.out.println("更新"+ i +"条记录成功");
            }
        }
    }

    /**
     * 更新表
     */
    public static void updateTable(){
        String sql = "select * from parking_order_august ";
        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(sql);
        for (Map<String, Object> map:list) {
            String plateNum = (String) map.get("PLATENUM");
            String paymentorderid = (String) map.get("PAYMENTORDERID");
            String parkId = (String) map.get("PARKID");
            String entryTime = (String) map.get("ENTRYTIME");
            String entryDate = entryTime.substring(0,10);
            String exitTime = (String) map.get("EXITTIME");
            String exitDate = exitTime.substring(0,10);
            Double payAmount = Double.parseDouble(map.get("PAYAMOUNT").toString());
            String payTime = (String) map.get("PAYTIME");

            /**更新paymentorder信息*/
            String selectMidSql = "select * from pims_payment_order where id=? ";
            List<Map<String, Object>> list3 = DruidJdbcUtils.executeQuery(selectMidSql, paymentorderid);
            Map<String, Object> paymentOrder = list3.get(0);
            if (null != paymentOrder){
                String select_mid = (String) paymentOrder.get("M_ID");
                String select_tid = (String) paymentOrder.get("T_ID");
                String parking_order_id = (String) paymentOrder.get("PARKING_ORDER_ID");

                String mId = mIdMap.get(parkId);
                String tId = tIdMap.get(mId);
                if (StringUtils.isEmpty(select_mid) || StringUtils.isEmpty(tId)){
                    System.out.println(select_mid);
                }
                if (!select_mid.equals(mId) || !select_tid.equals(tId)){

                    String updatePaymentOrder = "update pims_payment_order set m_id=?,t_id=? where id=? ";

                    System.out.printf("update pims_payment_order set m_id=%s,t_id=%s where id='%s' ",mId, tId, paymentorderid);
                    System.out.println();
                    appendMethod(appendFileName, "update pims_payment_order set m_id='"+ mId +"',t_id='"+ tId +"' where id='"+ paymentorderid +"';\r");
                    /*int i1 = DruidJdbcUtils.exectueUpdate(updatePaymentOrder, mId, tId, paymentorderid);
                    System.out.println("更新"+ i1 +"条数据成功");*/

                    String updateParkingOrder = "";
                    if ("854290065138998".equals(mId) && "13610178".equals(tId)){
                        updateParkingOrder = "update pims_parking_order set parking_name='FuHui',machine_mer='SuBin',parking_id='022' where id='"+ parking_order_id +"';";
                    }else if ("854290065138999".equals(mId) && "13610179".equals(tId)){
                        updateParkingOrder = "update pims_parking_order set parking_name='Daduhui',machine_mer='SuBin',parking_id='0432' where id='"+ parking_order_id +"';";
                    }else if ("854290065138191".equals(mId) && "13608503".equals(tId)){
                        updateParkingOrder = "update pims_parking_order set parking_name='Pudong Jinrong Parking',machine_mer='SuBin',parking_id='0507' where id='"+ parking_order_id +"';";
                    }
                    System.out.println(updateParkingOrder);
                    appendMethod(appendFileName1,updateParkingOrder+"\r");

                    String update_qrc_payment_record = "update qrc_payment_record set mid='"+ mId+"',tid='" + tId + "' where order_id='"+ paymentorderid +"';";
                    System.out.println(update_qrc_payment_record);
                    appendMethod(appendFileName2, update_qrc_payment_record +"\r");
                }
            }

            /**更新parkingorder数据*/
            /*String getparkingOrder = "select * from pims_parking_order where id =\n" +
                    "(select parking_order_id from pims_payment_order where id = ?)\n ";
            List<Map<String, Object>> list2 = DruidJdbcUtils.executeQuery(getparkingOrder, paymentorderid);
            Map<String, Object> parkingOrder = list2.get(0);
            if (parkingOrder != null){
                String updateParkId = (String) parkingOrder.get("PARKING_ID");
                String updateCarNo = (String) parkingOrder.get("CAR_NO");
                Double updateAmont = Double.parseDouble(parkingOrder.get("PARKING_FEE").toString());
                String updateVehicleInDate = (String) parkingOrder.get("VEHICLE_IN_DATE");
                String updateVehicleOutDate = (String) parkingOrder.get("VEHICLE_IN_DATE");
                String updateEntrydate = DateUtils.formatDate(DateUtils.dateFormat3,DateUtils.parseDate(DateUtils.dateFormat4,updateVehicleInDate));
                String updateExitdate = DateUtils.formatDate(DateUtils.dateFormat3,DateUtils.parseDate(DateUtils.dateFormat4,updateVehicleOutDate));


                if (updateParkId.equals(parkId) && updateCarNo.equals(plateNum) && updateAmont==payAmount
                        && updateEntrydate.equals(entryDate) && updateExitdate.equals(exitDate) ){
                    System.out.println("匹配成功");
                }else{
                    String judgeSql = "select * from (select a.*,substr(entrytime,1,10) as entrydate,substr(exittime,1,10) as exitdate from parking_order_july a) order_july \n" +
                            "where parkid=? and platenum=? and payamount=? and entrydate=? and exitdate=? ";
                    List<Map<String, Object>> list1 = DruidJdbcUtils.executeQuery(judgeSql, updateParkId, updateCarNo, updateAmont, updateEntrydate, updateExitdate);
                    System.out.printf("select * from (select a.*,substr(entrytime,1,10) as entrydate,substr(exittime,1,10) as exitdate from parking_order_july a) order_july \n" +
                                    "where parkid='%s' and platenum='%s' and payamount=%s and entrydate='%s' and exitdate='%s'"
                            ,updateParkId, updateCarNo, updateAmont, updateEntrydate,updateExitdate);
                    System.out.println();
                    if (list1.size()>=1){
                        System.out.println(list1.get(0) + "需要更新");
                        String updateSql = "update pims_parking_order set PARKING_NAME=?,MACHINE_MER=? where id=?";
                        System.out.println(updateSql);
                    }else {
                        System.out.println(updateCarNo + "需要插入记录");
                        String insertSql = "insert into pims_parking_order (ID, PARKING_NAME, MACHINE_MER, CHANNEL_CODE, CAR_NO, VEHICLE_IN_DATE, VEHICLE_IN_TIME, VEHILE_OUT_DATE, VEHILE_OUT_TIME, STAY_TIME, PARKING_FEE, STATUS, CREATE_TIME, UPDATE_TIME, MACHINE_ID, USER_ID, PAYMRNTNR, ROUTE_ID, PARKING_ID)\n" +
                                "values ('4a4589c01d984a1e9954b2ce978a31ef', 'Pudong Jinrong Parking', 'SuBin', 'LJZ', '豫A0M6C9', '20190728', '101000', '20190728', '150552', '295', '50.00', '1', '2019-07-28 10:10:16', '2019-07-28 15:05:11', null, null, '1', 'SuBin', '0507');\n  ";
                        System.out.println(insertSql);
                    }

                }

            }*/
        }
    }

    public static void updateQrcRecord(){
        String sql = "select * from parking_order_july ";
        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(sql);
        for (Map<String, Object> map:list) {
            String plateNum = (String) map.get("PLATENUM");
            String paymentorderid = (String) map.get("PAYMENTORDERID");
            String parkId = (String) map.get("PARKID");
            String entryTime = (String) map.get("ENTRYTIME");
            String entryDate = entryTime.substring(0,10);
            String exitTime = (String) map.get("EXITTIME");
            String exitDate = exitTime.substring(0,10);
            Double payAmount = Double.parseDouble(map.get("PAYAMOUNT").toString());
            String payTime = (String) map.get("PAYTIME");

            /**更新paymentorder信息*/
            String selectMidSql = "select * from qrc_payment_record_78 where order_id=? ";
            List<Map<String, Object>> list3 = DruidJdbcUtils.executeQuery(selectMidSql, paymentorderid);
            Map<String, Object> paymentOrder = list3.get(0);
            if (null != paymentOrder){
                String select_mid = (String) paymentOrder.get("MID");
                String select_tid = (String) paymentOrder.get("TID");

                String mId = mIdMap.get(parkId);
                String tId = tIdMap.get(mId);
                //if (!select_mid.equals(mId) || !select_tid.equals(tId)){
                if (!select_mid.equals(mId)){
                    String update_qrc_payment_record = "update qrc_payment_record_78 set mid='"+ mId+"',tid='" + tId + "' where order_id='"+ paymentorderid +"';";
                    System.out.println(update_qrc_payment_record);
                    appendMethod(appendFileName2, update_qrc_payment_record +"\r");
                }
            }


        }
    }

    public static void update_qrc_stlm_nortrans_dtlday(){
        String sql = "select * from parking_order_july ";
        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(sql);
        for (Map<String, Object> map:list) {
            String plateNum = (String) map.get("PLATENUM");
            String paymentorderid = (String) map.get("PAYMENTORDERID");
            String parkId = (String) map.get("PARKID");
            String entryTime = (String) map.get("ENTRYTIME");
            String entryDate = entryTime.substring(0,10);
            String exitTime = (String) map.get("EXITTIME");
            String exitDate = exitTime.substring(0,10);
            Double payAmount = Double.parseDouble(map.get("PAYAMOUNT").toString());
            String payTime = (String) map.get("PAYTIME");

            /**更新paymentorder信息*/
            String selectMidSql = "select * from qrc_payment_record_78 where order_id=? ";
            List<Map<String, Object>> list3 = DruidJdbcUtils.executeQuery(selectMidSql, paymentorderid);
            Map<String, Object> paymentOrder = list3.get(0);
            if (null != paymentOrder){
                String select_mid = (String) paymentOrder.get("MID");
                String platform_seq = (String) paymentOrder.get("PLATFORM_SEQ");
                String clear_date = (String) paymentOrder.get("CLEAR_DATE");

                String mId = mIdMap.get(parkId);
                String tId = tIdMap.get(mId);

                if (!select_mid.equals(mId)){
                    String update_qrc_stlm_nortrans_dtlday = "update qrc_stlm_nortrans_dtlday_78 set mid='"+ mId+"',tid='" + tId + "' where trans_seq='"+ platform_seq +"' and stlm_date='"+ clear_date +"'";
                    System.out.println(update_qrc_stlm_nortrans_dtlday);
                    appendMethod(appendFileName3, update_qrc_stlm_nortrans_dtlday +"\r");
                }
            }


        }
    }

    public static void updateExittime(){
        String sql = "select a.*,b.* from pims_payment_order a,parking_order_august b,pims_parking_order c \n" +
                "where a.id=b.paymentorderid\n" +
                "      and a.parking_order_id=c.id\n" +
                "      and parking_order_id in\n" +
                "          (select id from pims_parking_order where id in\n" +
                "          (select parking_order_id from pims_payment_order  where id in (select paymentorderid from parking_order_august))\n" +
                "          )\n" +
                "      and c.VEHILE_OUT_DATE is null ";
        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(sql);
        for (Map<String, Object> map:list) {
            String parkingOrderId = (String) map.get("PARKING_ORDER_ID");
            String exitDateTime = (String) map.get("EXITTIME");
            String exitDate = exitDateTime.substring(0,10);
            exitDate = exitDate.replace("-","");
            String exitTime = exitDateTime.substring(11);
            exitTime = exitTime.replace(":","");

            /**更新paymentorder信息*/
            String updateSql = "update pims_parking_order set VEHILE_OUT_DATE='"+ exitDate +"',VEHILE_OUT_TIME='"+ exitTime +"' where id='"+ parkingOrderId +"'; ";
            System.out.println(updateSql);
            appendMethod(appendFileName4, updateSql +"\r");

        }
    }

    public static void appendMethod(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateExMid(){
        String sql = "select * from pims_payment_order_temp where ex_m_id is null";
        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(sql);
        for (Map<String, Object> map:list) {
            String id = (String) map.get("ID");
            String updateSql = "update pims_payment_order_temp set ex_m_id=(select m_id from pims_payment_order_temp where id='" + id +"') where id='"+ id +"' ";
            System.out.println(updateSql);
            int i = DruidJdbcUtils.exectueUpdate(updateSql);
            if (i != 1){
                System.out.println(id);
            }

        }
    }

}
