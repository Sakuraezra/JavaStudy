package JavaConponsGreedy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ author ezra
 * @ date 2019/5/9 16:59
 */
public class GreedyCoupon {
	public static Map<Integer, BigDecimal> couponsGreedy(List<Coupon> senseAgroDiscountCoupons, BigDecimal showPrice) {
		Map<Integer, BigDecimal> result = new HashMap<>();
		int lineCouponId = 0;
		int discountCouponId = 0;
		int maxLine = 0;
		int maxPriceDiscount = 0;
		int minDiscount = 10;
		BigDecimal minLinePrice ;
		BigDecimal minDiscountPrice ;
		for (Coupon senseAgroDiscountCoupon : senseAgroDiscountCoupons) {
			//CouponType : 0为满减 1为折扣
			if (senseAgroDiscountCoupon.getType() == 0) {
				if (maxLine < senseAgroDiscountCoupon.getPriceLine()) {
					maxPriceDiscount = senseAgroDiscountCoupon.getPriceDiscount();
					lineCouponId = senseAgroDiscountCoupon.getCouponId();
				}
			} else if (senseAgroDiscountCoupon.getType() == 1) {
				if (minDiscount > senseAgroDiscountCoupon.getDiscount()) {
					minDiscount = senseAgroDiscountCoupon.getDiscount();
					discountCouponId = senseAgroDiscountCoupon.getCouponId();
				}
			}
		}

		minLinePrice = showPrice.subtract(new BigDecimal(maxPriceDiscount));
		minDiscountPrice = showPrice.multiply(new BigDecimal(minDiscount)).divide(new BigDecimal(10));
		if (minDiscountPrice.compareTo(minLinePrice) >= 0) {
			result.put(lineCouponId, minLinePrice);
		} else {
			result.put(discountCouponId, minDiscountPrice);
		}
		return result;
	}

	public static void main(String[] args) {
		List<Coupon> list = new ArrayList<>();
		Coupon coupon_1  = new Coupon();
		Coupon coupon_2  = new Coupon();
		Coupon coupon_3  = new Coupon();
		Coupon coupon_4  = new Coupon();
		Coupon coupon_5  = new Coupon();

		coupon_1.setCouponId(1);
		coupon_1.setDiscount(3);
		coupon_1.setPriceDiscount(-1);
		coupon_1.setPriceLine(-1);
		coupon_1.setType(1);

		coupon_2.setCouponId(2);
		coupon_2.setDiscount(5);
		coupon_2.setPriceDiscount(-1);
		coupon_2.setPriceLine(-1);
		coupon_2.setType(1);

		coupon_3.setCouponId(3);
		coupon_3.setDiscount(2);
		coupon_3.setPriceDiscount(0);
		coupon_3.setPriceLine(0);
		coupon_3.setType(1);

		coupon_4.setCouponId(4);
		coupon_4.setDiscount(-1);
		coupon_4.setPriceDiscount(2);
		coupon_4.setPriceLine(5);
		coupon_4.setType(0);

		coupon_5.setCouponId(5);
		coupon_5.setDiscount(-1);
		coupon_5.setPriceDiscount(9);
		coupon_5.setPriceLine(10);
		coupon_5.setType(0);


		list.add(coupon_1);
		list.add(coupon_2);
		list.add(coupon_3);
		list.add(coupon_4);
		list.add(coupon_5);

		Map result = couponsGreedy(list,new BigDecimal(10));
		System.out.println(result);
	}
}
