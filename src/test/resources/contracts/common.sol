#define WORD_0 0   /* 32*0 = 0 */
#define WORD_1 32   /* 32*1 = 32 */
#define WORD_2 64   /* 32*2 = 64 */
#define WORD_3 96   /* 32*3 = 96 */
#define WORD_4 128  /* 32*4 = 128 */
#define WORD_5 160  /* 32*5 = 160 */
#define WORD_6 192  /* 32*6 = 192 */
#define WORD_7 224  /* 32*7 = 224 */
#define WORD_8 256  /* 32*8 = 256 */
#define WORD_9 288  /* 32*9 = 288 */
#define WORD_10 320 /* 32*10 = 320 */
#define WORD_11 352 /* 32*11 = 352 */
#define WORD_12 384 /* 32*12 = 384 */
#define WORD_13 416 /* 32*13 = 416 */
#define WORD_14 448 /* 32*14 = 448 */

#define REVERT(code) \
  mstore(WORD_1, code) revert(const_add(WORD_1, 31), 1)

#define DEBUG_REVERT(data) \
  mstore(WORD_1, data) revert(WORD_1, WORD_1)

#define SAFE_MUL(A, B) \
  mul(A, B) \
  if iszero(or(iszero(A), iszero(B))) { \
    if iszero(eq(div(mul(A, B), B), A)) { \
      REVERT(100) \
    } \
  }

#define SAFE_ADD(A, B) \
  add(A, B) \
  if lt(add(A, B), B) { \
    REVERT(101) \
  }

#define SAFE_SUB(A, B) \
  sum(A, B) \
  if gt(B, A) { \
    REVERT(102) \
  }
