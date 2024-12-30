const required = (
  message = '必填项',
  trigger: Trigger = 'blur'
): {
  [key: string]: any;
} => {
  return { required: true, message, trigger };
};

const min = (
  min: number,
  message = `长度不能小于${min}`,
  trigger: Trigger = 'blur'
): {
  [key: string]: any;
} => {
  return { min, message, trigger };
};

const max = (
  max: number,
  message = `长度不能大于${max}`,
  trigger: Trigger = 'blur'
): {
  [key: string]: any;
} => {
  return { max, message, trigger };
};

export const Validator = { required, min, max };

type Trigger = 'blur';
