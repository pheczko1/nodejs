/*import org.jredis.ClientRuntimeException;
import org.jredis.JRedis;
import org.jredis.ProviderException;
import org.jredis.RedisException;
import org.jredis.protocol.Command;
import org.jredis.ri.alphazero.JRedisClient;
import static org.jredis.ri.alphazero.support.DefaultCodec.*;

*//**
 * [TODO: document me!]
 *
 * @author  Joubin Houshyar (alphazero@sensesay.net)
 * @version alpha.0, Apr 15, 2009
 * @since   alpha.0
 * 
 *//*

public class HelloAgain {
    public static final String key = "jredis::examples::HelloAgain::message";
    public static void main(String[] args) {
        String password = "";
        if(args.length > 0) password  = args[0];
        new HelloAgain().run(password);
    }

    private void run(String password) {
        try {
            JRedis  jredis = new JRedisClient("localhost", 6379, "jredis", 0);
            jredis.ping();

            if(!jredis.exists(key)) {
                jredis.set(key, "Hello Again!");
                System.out.format("Hello!  You should run me again!\n");
            }
            else {
                String msg = toStr ( jredis.get(key) );
                System.out.format("%s\n", msg);
            }
            jredis.quit();
        }
        catch (RedisException e){
            if (e.getCommand()==Command.PING){
                System.out.format("I'll need that password!  Try again with password as command line arg for this program.\n");
            }
        }
        catch (ProviderException e){
            System.out.format("Oh no, an 'un-documented feature':  %s\nKindly report it.", e.getMessage());
        }
        catch (ClientRuntimeException e){
            System.out.format("%s\n", e.getMessage());
        }
    }
}

*/