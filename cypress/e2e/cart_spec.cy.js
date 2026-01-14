describe('Cart Test', () => {
  beforeEach(() => {
    cy.visit('https://www.saucedemo.com');
    cy.get('#user-name').type('standard_user');
    cy.get('#password').type('secret_sauce');
    cy.get('#login-button').click();
  });

  it('Should add a product to the cart', () => {
    cy.get('.inventory_item').first().find('.btn_inventory').click();
    cy.get('.shopping_cart_badge').should('have.text', '1');
  });

  it('Should sort products by price low to high', () => {
    cy.get('.product_sort_container').select('lohi');
    cy.get('.inventory_item_price').first().should('have.text', '$7.99');
  });

  // Kịch bản mới: Xóa sản phẩm khỏi giỏ hàng
  it('Should remove product from cart', () => {
    cy.get('.inventory_item').first().find('.btn_inventory').click();
    cy.get('.shopping_cart_badge').should('have.text', '1');

    cy.get('.inventory_item').first().find('.btn_inventory').click();

    cy.get('.shopping_cart_badge').should('not.exist');
  });

  // Kịch bản mới: Quy trình thanh toán
  it('Should go to checkout step two after filling information', () => {
    cy.get('.inventory_item').first().find('.btn_inventory').click();

    cy.get('.shopping_cart_link').click();
    cy.url().should('include', '/cart.html');

    cy.get('#checkout').click();
    cy.url().should('include', '/checkout-step-one.html');

    cy.get('#first-name').type('John');
    cy.get('#last-name').type('Doe');
    cy.get('#postal-code').type('12345');
    cy.get('#continue').click();

    cy.url().should('include', '/checkout-step-two.html');
  });
});
