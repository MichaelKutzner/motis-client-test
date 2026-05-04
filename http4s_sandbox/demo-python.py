#!/usr/bin/env -S uv run --script
# /// script
# dependencies = [
#     "example-client @ file://${PROJECT_ROOT}/python-client",
# ]
# ///

from example.client.models import MyObject, MyEither, MyLeft, MyRight

def main():
    either = MyEither.from_dict({'left': 'Hello, World!', 'value': 2.71, 'eitherType': 'MyLeft'})
    print(f"Either: '{either.to_json()}'")
    print()
    o = MyObject.from_dict({'id': 5, 'feature': either.to_dict()})
    print(f"Object: '{o}'")
    print(o.to_json())
    o2 = MyObject(id=123, value='Object (2)', feature=MyEither(MyRight(right='Hello', value='World')))
    print()
    print(f"Object(2): '{o2}'")
    print(f"Object(2).to_json(): '{o2.to_json()}'")


if __name__ == '__main__':
    main()

