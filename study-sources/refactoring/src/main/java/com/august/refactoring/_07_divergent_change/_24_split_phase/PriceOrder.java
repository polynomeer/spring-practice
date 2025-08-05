package com.august.refactoring._07_divergent_change._24_split_phase;

public class PriceOrder {

    public double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {
        final PriceData priceData = calculatePriceData(product, quantity);
        return applyShipping(priceData, shippingMethod);
    }

    /**
     * 제품과 구매량을 받아서 기본 할인율을 적용
     *
     * @param product  제품
     * @param quantity 제품의 구매량
     * @return 제품의 구매량에 따른 기본가격
     */
    private PriceData calculatePriceData(Product product, int quantity) {
        final double basePrice = product.basePrice() * quantity;
        final double discount = Math.max(quantity - product.discountThreshold(), 0)
                * product.basePrice() * product.discountRate();
        final PriceData priceData = new PriceData(basePrice, discount, quantity);
        return priceData;
    }

    /**
     * 배송 수단에 따라 할인율을 적용하는 메서드
     *
     * @param priceData      price 관련 데이터
     * @param shippingMethod 배송수단 (price와 직접적인 연관이 없으므로 파라미터를 분리)
     * @return 할인이 계산된 price
     */
    private double applyShipping(PriceData priceData, ShippingMethod shippingMethod) {
        final double shippingPerCase = (priceData.basePrice() > shippingMethod.discountThreshold()) ?
                shippingMethod.discountedFee() : shippingMethod.feePerCase();
        final double shippingCost = priceData.quantity() * shippingPerCase;
        final double price = priceData.basePrice() - priceData.discount() + shippingCost;
        return price;
    }
}
