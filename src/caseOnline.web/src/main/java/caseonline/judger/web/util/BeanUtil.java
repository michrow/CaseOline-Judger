package caseonline.judger.web.util;

import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  

/**
 * The Class BeanUtil.
 */
public abstract class BeanUtil {  
    
    /**
     * Bean深拷贝
     *
     * @param <T> the generic type
     * @param src the src
     * @return the t
     * @throws RuntimeException the runtime exception
     */
    @SuppressWarnings("unchecked")  
    public static <T> T cloneTo(T src) throws RuntimeException {  
        ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();  
        ObjectOutputStream out = null;  
        ObjectInputStream in = null;  
        T dist = null;  
        try {  
            out = new ObjectOutputStream(memoryBuffer);  
            out.writeObject(src);  
            out.flush();  
            in = new ObjectInputStream(new ByteArrayInputStream(memoryBuffer.toByteArray()));  
            dist = (T) in.readObject();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } finally {  
            if (out != null)  
                try {  
                    out.close();  
                    out = null;  
                } catch (IOException e) {  
                    throw new RuntimeException(e);  
                }  
            if (in != null)  
                try {  
                    in.close();  
                    in = null;  
                } catch (IOException e) {  
                    throw new RuntimeException(e);  
                }  
        }  
        return dist;  
    }  
}  
