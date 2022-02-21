package com.august.refactoring._16_temporary_field._36_introduce_special_case;

public class CustomerService {

    public String customerName(Site site) {
        Customer customer = site.getCustomer();

        String customerName;
        if (isUnknown(customer)) {
            customerName = "occupant";
        } else {
            customerName = customer.getName();
        }

        return customerName;
    }

    public BillingPlan billingPlan(Site site) {
        Customer customer = site.getCustomer();
        return isUnknown(customer) ? new BasicBillingPlan() : customer.getBillingPlan();
    }

    public int weeksDelinquent(Site site) {
        Customer customer = site.getCustomer();
        return isUnknown(customer) ? 0 : customer.getPaymentHistory().getWeeksDelinquentInLastYear();
    }

    private boolean isUnknown(Customer customer) {
        return customer.getName().equals("unknown");
    }

}
