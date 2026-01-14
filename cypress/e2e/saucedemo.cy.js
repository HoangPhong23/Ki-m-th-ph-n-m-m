describe('SauceDemo E2E Test', () => {
  it('Đăng nhập thành công', () => {
    cy.visit('https://www.saucedemo.com/');

    cy.get('#user-name').type('standard_user');
    cy.get('#password').type('secret_sauce');
    cy.get('#login-button').click();

    cy.url().should('include', '/inventory.html');
    cy.contains('Products').should('be.visible');
  });
});
