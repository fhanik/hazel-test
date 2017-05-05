package hanik.filip.hazeltest;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;

import java.util.concurrent.ConcurrentMap;

import static java.util.Optional.ofNullable;

public class SimpleHazelCoordination {

    public static String I_WAS_HERE_FIRST = "i_was_here_first";

    public static void main(String[] args) {
        Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        Member localMember = instance.getCluster().getLocalMember();
        ConcurrentMap<String, Member> mapCustomers = instance.getMap("coordination");
        Member coordinator = ofNullable(mapCustomers.putIfAbsent(I_WAS_HERE_FIRST, localMember)).orElse(localMember);
        if (coordinator.equals(localMember)) {
            System.out.println("We are started!");
        } else {
            System.out.println("System already running, originally started by:"+coordinator);
        }
    }
}
