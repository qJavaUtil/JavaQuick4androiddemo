package com.example.qjava4a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import blxt.qjava.autovalue.QJavaApplication;
import blxt.qjava.autovalue.autoload.AutoLoadBase;
import blxt.qjava.autovalue.autoload.AutoValue;
import blxt.qjava.autovalue.inter.ConfigurationProperties;
import blxt.qjava.autovalue.inter.Value;
import blxt.qjava.autovalue.reflect.PackageUtil;
import blxt.qjava.autovalue.util.ObjectPool;
import blxt.qjava.autovalue.util.QThreadpool;
import blxt.qjava.autovalue.util.ValueFactory;
import com.blxt.qfile.QFile;
import com.blxt.quickfile4a.QFile4a;
import com.example.qjava4a.config.AppConfiguration;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {
    private TextView tvHellow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //boolean falSetAccessible = JavaVersionUtils.getJDKVersion() <= 52;
        tvHellow = (TextView) findViewById(R.id.tv_hellow);

        AppConfiguration appConfiguration = (AppConfiguration)ObjectPool.getObject(AppConfiguration.class);

        try {
            InputStream in = this.getAssets().open("config/application.properties");
            AutoValue.setPropertiesFile(in, "utf-8");
            ValueFactory valueFactory = new ValueFactory();
            valueFactory.autoVariable(AppConfiguration.class, true);
            appConfiguration = (AppConfiguration)ObjectPool.getObject(AppConfiguration.class);

            System.out.println("结果:" + appConfiguration.toString());

            tvHellow.setText("结果:" + appConfiguration.toString());
            //   autoValue.autoVariable(QThreadpool.class, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
//            Scanner.scan();
             QJavaApplication.run(MainActivity.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }


        QThreadpool qThreadpool = (QThreadpool)ObjectPool.getObject(QThreadpool.class);
        System.out.println( "结果:" + appConfiguration.toString());

        // 获取f对象对应类中的所有属性域
        Field[] fields = AppConfiguration.class.getDeclaredFields();
        // 遍历属性
        for (Field field : fields) {
            // 过滤 final 元素
            if ((field.getModifiers() & 16) != 0) {
                continue;
            }

            Value valuename = field.getAnnotation(Value.class);
            if(valuename != null){
                System.out.println("元素:" + valuename.value().trim());
            }

        }

        //       Scanner.scan();

       // System.out.println("本地IP: " + getLocalIpAddress());
       // System.out.println("本地IP: " + getIPAddress(this));
        //        // 测试效果
       // AutowireEntry autowireEntry = (AutowireEntry) ObjectPool.getObject(AutowireEntry.class);
       // System.out.println(autowireEntry.toString());

    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("get IpAddress fail", ex.toString());
            return "";
        }
        return "";
    }


    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

}