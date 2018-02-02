package com.threehmis.xcjc.api;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import com.threehmis.xcjc.api.bean.BaseBeanRsp;
import com.threehmis.xcjc.api.bean.BaseEntity;
import com.threehmis.xcjc.api.bean.BaseResult;
import com.threehmis.xcjc.api.bean.ItemResult;
import com.threehmis.xcjc.api.bean.PictureResult;
import com.threehmis.xcjc.api.bean.XCJCResponseBean;
import com.threehmis.xcjc.api.bean.request.ChangeAddressRequestBean;
import com.threehmis.xcjc.api.bean.respon.ChangeAddressResponBean;
import com.threehmis.xcjc.api.bean.respon.GetExamReportRsp;
import com.threehmis.xcjc.api.bean.respon.GetLoginListRsp;
import com.threehmis.xcjc.api.bean.respon.GetMainAddressRsp;

import java.util.Map;

/**
 * Created by zhengchengrong on 2017/9/1.
 */

public interface BjajService {


    //登陆
    @POST("login")
    Observable<XCJCResponseBean<UserBean>> toLogin(@Body UserBean userBean);

    //获取任务数量
    @GET("todo/count")
    Observable<XCJCResponseBean<TodoGroupBean>> getWorkNum(@QueryMap Map<String,String> params);


    //获取任务列表
    @GET("jcWtInfo/list")
    Observable<XCJCResponseBean<TaskDownResBean>> getWorkList(@QueryMap Map<String,String> params);

    //获取所有部门接口
    @GET("orgnization/list")
    Observable<XCJCResponseBean<OrginzatioinBean>> getOrginzationList(@QueryMap Map<String,String> params);
    //获取所有用户接口
    @GET("user/list")
    Observable<XCJCResponseBean<OrginzatioinUserBean>> getOrginzationUserList(@QueryMap Map<String,String> params);

    //确认下达
    @POST("jcWtInfo/department")
    Observable<XCJCResponseBean<UserBean>> sureDown(@Body TaskSureDownBean taskSureDownBean);

    // 上传图片
    @Multipart
    @POST("upload/pic")
    Observable<BaseResult<PictureResult>> uploadPic(@Part("description") RequestBody description,
                               @Part MultipartBody.Part file);


/*    @POST("monitorunit/getMonitorunit")
    Observable<BaseBeanRsp<ChangeAddressResponBean>> getMonitorunitStr(@Body ChangeAddressRequestBean params);*/

}
