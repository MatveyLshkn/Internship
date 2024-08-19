package lma.repository;

import jakarta.transaction.Transactional;
import lma.entity.TokenPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenPairRepository extends JpaRepository<TokenPair, Long> {

    boolean existsTokenPairByRefreshTokenAndUser_Id(String refreshToken, Long userId);

    @Modifying
    void deleteByRefreshToken(String refreshToken);

    @Transactional
    @Modifying
    @Query("UPDATE TokenPair t SET t.accessToken = :newAccessToken, t.refreshToken = :newRefreshToken WHERE t.refreshToken = :oldRefreshToken")
    void updateTokenPairByRefreshToken(@Param("newAccessToken") String newAccessToken,
                                       @Param("newRefreshToken") String newRefreshToken,
                                       @Param("oldRefreshToken") String oldRefreshToken);
}
