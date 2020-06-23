package io.floodgate.sdk.utils;

import io.floodgate.sdk.User;
import io.floodgate.sdk.models.FeatureFlag;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class RolloutHelper {
    public static int GetScale(FeatureFlag flag, User user) {
        var id = String.format("%s%s", flag.key, user.getId());

        try {
            var sha1 = MessageDigest.getInstance("SHA-1");
            sha1.reset();
            sha1.update(id.getBytes());

            var sha = byteToHex(sha1.digest());

            return Math.abs(new BigInteger(sha, 16).mod(BigInteger.valueOf(100)).intValue());

        } catch (NoSuchAlgorithmException e) {
            // All java platforms required to support SHA-1
            var hash = id.hashCode();
            return Math.abs(hash % 100);
        }
    }

    private static String byteToHex(final byte[] hash)
    {
        var formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        var result = formatter.toString();
        formatter.close();
        return result;
    }
}
