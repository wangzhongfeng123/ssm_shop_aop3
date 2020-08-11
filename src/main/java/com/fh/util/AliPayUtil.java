package com.fh.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.yaml.snakeyaml.events.Event;

import java.math.BigDecimal;

import static com.alipay.api.AlipayConstants.APP_ID;

public class AliPayUtil {



    public static String  aliPay(String id){
        AlipayClient alipayClient =  new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do" ,
                "2021000116666636",
                //应用私钥
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCNAEOd0kwYVqYT3Gn2Fc58a9mb1RbMA3YKjIYNCfA5m8Hkv+25Y4Wk/hWXlsNb0z4bZ5e0AXrGdyX3dCYMh9YYlLcl6F7xZE73KynU3i5/TNpXx/u5ESl9aSmhig55n7XXWnzbGyw4vf5emA1hLjYxqi66J/UiQfwIVLxN92/qI08y47srvxEK7zijlbDOza41RlTXYE+SQrFWAQxxxlkmtc9klTTSWZ+C0eLu7yvAeZxLMbYskH4G5m60JpxjWDZX/U+XObFVO6SYIYj0P51D6TpwDKFuTzxgFLOnLhgmt3FOgp2ExCVZhiIRU7f3r2idy4De3pRpNUbliOZ66+alAgMBAAECggEAQ3w34KaRgyidrm3VTVHclVJtQZd4pMaXTa86Ta1PC9OPtrNhzVRj17dAkGA5Oo7db/Jh+mam46yNQGWX/HFbOEKdcghIwNJYtRY0nEyHBqi67D6sLUSYjgyyPz27ddzKBLurfJcB0FqnD5FI/hVHHPguCy0qt23LehVMVyixqObtNUXoTQIH08QEFL8oAKLyCBk/DkXON+QoVUN36jwmvu/t3K8TRTmTNNANFZhfsxg9waXjdfTSF9ofLy0mKEOPSD7hV+tdgIoC1C3iIFAvxCtC2XXLfPjgBxENvtUPHSJPTkwK19GjMdTUHx48imqF0MmTYqT1j7tuCHQGX6A4YQKBgQDKx5fP/rDJFzgpZy7AE3bzMeQdUG9EfhVGVTs/7zlOx9Fz/dRSfU6Gq+dpr7Esp4XPuMk7+/8xtUs0Fc3rhd4zRiLFTovROhKqsC+iBaa9MKFTbnC1+gueSih88Z+hd7s7xbhRIRfAJzU3oL3iyLphDY/6mY9xE+49gAm/I0kOOQKBgQCyAd/Ot72Q08OpeyMe7WCE0A3Xtze3pZhOe8Z/0b2qot8P2IyJd8vJAl+dm/G7yUXMYH7hLA1Pk+7C1KI28B6hy4qgbXLp6KULIAhgTrITwkMSFaw8IcYBNwqLDO0lG7uj7eIYlOY6KE1ChL26igWi3W/DtVJ3WXsPRg7vbEebzQKBgQCBs97f1HnBdg+fM/eMNicMcSoGsg8pkTgMb7HhOCPAx0Cq332ycaILs+LmD9HUzSZi8kANFSdq5RpAK342Gr9ArqjI6W+3GV0dPmqJSPIY6wUVd3uyekh6K9bv7CD9x7bECbjWc8NcTffVKAStDVVhhKpGUXiVjHkDb2VMk8ZDyQKBgHjfmPAwCINNqPFu8jxQCFhnRt8SRL1prvCsT+//poE2M6346AV42WJXQEIv4IgZdlH/vGh+l1NQKrMV3Ejd6I8JPcsORxfaqCfTypxLE9Ui9pRTLy9t55xvmbMuReIK4PHB2OPESEl5kGz44jTHAGjFYN1ORP9SPaPzeLTPnBiJAoGBAK3L/wO6k05KOM16Ko+0zCWTv/jn1taggbveHH2SzHTrLbLMdL+hqR51lVllYCbMN1cTEl6+IOBoAxW+CM6S+XUS5kkEP4IOB1eelGfKmyl9y93GT1iUyjJLxCJthUr/GAkt0WO0S2TV5lygkeqArPrxG6DmsOM6VP0vFUgjfJm3",
                "JSON",
                "utf-8",
                //支付宝公钥
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvxnW2K0zh3WjAam9ig8+vUXd+7Uq+R6aTz5MB+5mRDB3H4gsFgCyoQeI+vmKDVeHefDKx4G2k4hkKNEdLxP9mJpwWKYXlQK9NOwpsd40wPQTuNo94TtZekXRrErUm6OLKT21lC8Czl5n3zqybS9qFJgw/uj//ucLVRd5e3mon0VxlZemWsuSWCXgiQxIlwM0paJ4euNjELHJHZ8YSNlUM3sPZ/VUrxyPf5OdgXSBK9/TG+umkr9qvoz194pi+bn6jfQ3zWAv77+W8JFBe1dytxXDY5L3iR6egUNgQBmOMXaxSpiFxDZjzybc7bah58NYvC8gttq57Fyp7gbhBs7vgwIDAQAB",
                "RSA2");  //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl( "http://localhost:8081/success.html" );
        alipayRequest.setBizContent( "{"  +
                "    \"out_trade_no\":\"1908a_wzf_"+id+"\","  +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","  +
                "    \"total_amount\":"+1+","  +
                "    \"subject\":\"母婴商城\""  +
                "    }" +
                "  }" ); //填充业务参数
        String form= "" ;
        try  {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    public static void queryAliPayStatus(String id) throws AlipayApiException {
        AlipayClient alipayClient =  new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do" ,
                "2021000116677545",
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCPGKk/92iOisPBTHq05hMy4bHGx3/CLIjaCQ+zIl+AnK2+3QyMxyRVa0a/L4DKTCCdiUhqJWIZETE7zWnZ+E6td0kyZOyHlvB3FuFcUW53OSs3LcU7Z0WdvTWVeAJI/Lf7Km+277fnb8IgvZFF1toO5uLu5n3m3Rksq7SWHgufP2561MKC9jL+fw5Xb8VOZPYDQjcXVSOdsjo9p2dQzDTyr8iCMHln3n36X406yui8nU9rbxqJxqo3GdqpiLARvegx5+N/vV3r8BkFw1XGRVSWwIpS8A0fr6nELQSr0qYNM0YYAKUbQ6+BcPAWFi7vYRcssep6X+dkJrKalVFYq02xAgMBAAECggEAXcUpU9OK3q2sLcUyO8zxE8y2NK6S1+lbHJYHhbmmn58MdCBikvYEpz0PNUWpvec5EELgAz7QE+EiRDANh1qiJCYg/zcHkKqmlUOXFmn+W35JBXWm45B/HCjbLkFgLff9kEd0wYpBFxYzPkU7J2rtpgpC945vPpPwZUd3pU+3CfkHVAuKNPwVlu5/QkGgTxeUoqaFxiXSWy3wXvftMwEc7M+TPpFNFcD0a/o9GiQyal87bR+3H0FTpNoMJw3eicXBFVm1LBS2g/l7rjWf3njRYcQf2dn9IVpz4vaO6X+cvdnGeyare75u+BE9Z8cEtbwIunGMJqGSwWvdMi+xoBXx4QKBgQDfVc3OyNGWWPzdSYPEoQ2NFZgnqjw0hac4TqRmydSVPrcq8D6wsDmVvIZ4rg51T1YsRTviYkgHfGgGjHunOnK99MuLdAl8Dh8jS38aFT0sKtk7Akw9TMzppo976/XHCBqxg0MQUpVtxClFWT8JQR9FfD1VXSy2K23yzkC20RfshQKBgQCkBoaQJuGhh0kxD9L5oICN3HdIOvG1LtwfB5rc4w/+IyDgJ0BvamneYGff+84guI4fLVWi9Qzb8Q+BWEFTnhovPUXdbgMBNIZLdc2MuXTjDl3sbzuJXnVfAML4R2mGXxqImxmaWVeUeYa5q3e1hsRlvr+IP8ySYShAY1QdryvKPQKBgQCoo/L/dTaFz/ZI5m0gefhwk2DMeshxZIbrhr6vezIR7ESFoNnFs5wYDD6CO/Rg8qKAVVsty8bWGEO4xjXNBd6Ev6c/S0SqL0Ol9CkqueTyR/y2iTqmgyqsOABas8doHh+B8TsfxSxesWP0oLpvs4P4hSMPJYdQm6SvVr0ZDvSAvQKBgAL9p3h7bQPP/rKpUjSBC7cKfRZRFNUz7ImYTueWMFV2+IN907v0WcfXRC2eXszT8Kt63SPiigB8rcazAEXxDRMtb9ewyNc9yPEZTc0yGIKHYYkNIi3IDiXUUrnKO49arpYtFdNtq54mdSFvkfg6Y9xjFQT7/NPyd5Rs+zyrVAYVAoGBAN1eghr6WP4DBTkzzad3mJgiebZPqjY7hTmM6uYPeUpPMa2tRha3FiicaYvgsOfVT3Kyw5pTvJuqnrZw06/OdXUQQPhXJkvQxEEL8Q2KY8EvXR2A0FCPFAuzryuYBofqfzssDFs2U2K+6cTxpMPetpIHq0PWKnBZQ+HdRCr07Okj",
                "JSON",
                "utf-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv6PuEyyob2cGwqec09s8ma//N/1XM77F7YQ7LzQvKIvkD+rmCrPVsIjH99QhheX4byWmyxkBZQiffzrJJPt+PkIPd2uj7Ue6oWv+J/mUIXf1YOyXycDtZT60vg5gU95UPflkDS1btq/UEHTP8lAIlJtHRFoysmrYEoPwVgU2R6nj/PdW5bAbYVtGaAQfHhDp6VJoXOQoYrDjQXiWl82McZNOMm+OATTq4sjPpJC5l0XjZ+oQVLbFmQ25t7dRdPQqMPb7gwBnhKrlVVPzxnWLXfTo5GGIP/mBQKtgv/RlYsLdIg0kKpc/DLCxtKZV0E4newhhOqOwgLLde4JemdUkOwIDAQAB",
                "RSA2");  //获得初始化的AlipayClient
        AlipayTradeQueryRequest request =  new  AlipayTradeQueryRequest();
        request.setBizContent( "{"  +
                "\"out_trade_no\":\"1908a_wzf_"+id+"\""  +
                " }" );
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()){
            System.out.println( "调用成功" );
            String tradeStatus = response.getTradeStatus();
            System.out.println(tradeStatus);
        }  else  {
            System.out.println( "调用失败" );
        }
    }

    public static void main(String[] args) throws AlipayApiException {
        String id = String.valueOf(69);
        queryAliPayStatus(id);
    }

}
