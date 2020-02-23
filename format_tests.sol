pragma solidity ^0.5.7;

import "./contracts-compiled/MarginProxy/MarginProxy.sol";
import "./contracts-compiled/MarginParent/MarginParent.sol";
import "./contracts-compiled/MarginSwap/MarginSwap.sol";

contract TestUserFullControl {
  function test_onlyOwnerCanUpdateCode() public {
    Audit audit = Audit(address(0x100200300));

    MarginSwap code = new MarginSwap();
    MarginParent parent = new MarginParent(address(code));

    address my_address = address(this);
    address margin_user = address(audit.createVariable());
    address attack_user = address(audit.createVariable());

    audit.assume(margin_user != my_address);
    audit.assume(attack_user != margin_user);
    audit.assume(attack_user != address(parent));

    audit.useAddress(margin_user);

    address payable margin_contract = address(uint160(parent.setupMargin()));
    MarginProxy proxy = MarginProxy(margin_contract);

    audit.assert(margin_contract != address(0), "should create margin address");
    audit.assert(proxy.getCode() == address(code), "code should match that provided in constructor");

    audit.useAddress(attack_user);
    // audit.anyCall(address(parent), 10);
    // audit.anyCall(margin_contract, 10);

    audit.anyCall(margin_contract, 324);
    // audit.anyCall(address(parent), 324);
    // proxy.setCode(address(audit.createVariable()));

    audit.useAddress(margin_user);
    address code_address = address(proxy.getCode());
    audit.assert(code_address == address(code), "code should not change");
  }
}

// contract Tests {
//   function test_simple() public {
//     Audit audit = Audit(address(0x100200300));
//     
//     MarginSwap code = new MarginSwap();
//     MarginParent parent = new MarginParent(address(code));
// 
//     address my_address = address(this);
//     address some_user = address(audit.createVariable());
//     audit.assume(some_user != my_address);
// 
//     audit.assume(uint256(some_user) == 1);
// 
//     address expected_address = parent.getMarginAddress(some_user);
//     audit.assert(uint256(expected_address) != 0, "non zero");
// 
//     audit.useAddress(some_user);
//     address payable margin_contract = address(uint160(parent.setupMargin()));
//     audit.assert(uint256(margin_contract) != 0, "non zero");
//     audit.assert(margin_contract == expected_address, "should be the same");
//     audit.useAddress(my_address);
// 
//     uint256 send_amount = audit.createVariable();
//     uint256 balance = address(this).balance;
//     audit.assume(address(parent).balance == 0);
//     audit.assume(balance >= send_amount);
//     audit.assume(send_amount > 100);
// 
//     require(address(parent).send(send_amount));
//     audit.assert(address(this).balance == balance - send_amount, "should debit");
//     audit.assert(address(parent).balance == send_amount, "should credit");
//   }
// 
//   function test_marginAddressShouldBeSame() public {
//     Audit audit = Audit(address(0x100200300));
// 
//     MarginSwap code = new MarginSwap();
//     MarginParent parent = new MarginParent(address(code));
// 
//     address user_1 = address(audit.createVariable());
//     address user_2 = address(audit.createVariable());
// 
//     audit.assume(user_1 == user_2);
//     address m_user_1 = parent.getMarginAddress(user_1);
//     address m_user_2 = parent.getMarginAddress(user_2);
// 
//     audit.assert(m_user_1 == m_user_2, "should be different");
//   }
// 
//   function test_marginAddressShouldBeUnique() public {
//     Audit audit = Audit(address(0x100200300));
// 
//     MarginSwap code = new MarginSwap();
//     MarginParent parent = new MarginParent(address(code));
// 
//     address user_1 = address(audit.createVariable());
//     address user_2 = address(audit.createVariable());
// 
//     audit.assume(user_1 != user_2);
//     address m_user_1 = parent.getMarginAddress(user_1);
//     address m_user_2 = parent.getMarginAddress(user_2);
// 
//     audit.assert(m_user_1 != m_user_2, "should be different");
//   }
// }


contract Audit {
  function assume(bool condition) external view;

  function assert(bool condition, string calldata error_message) external view;

  function setTimestamp(uint256 timestamp) external view;

  function useAddress(address updated_address) external view;

  function createVariable() external view returns (uint256);

  function anyCall(address other_contract, uint256 byte_count) external view;

  // function expectRevert(bytes calldata expected_message) external view;

  // function expectRevert() external view;

  // function logsShouldMatch() external view;
}
