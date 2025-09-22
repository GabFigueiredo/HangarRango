import { PatternFormat } from 'react-number-format';
import { Input } from './input';

export function CPFInput({ value, onChange }: { value: string; onChange: (value: string) => void }) {
    return <PatternFormat
            format='###.###.###-##'
            customInput={Input}
            placeholder='999.999.999-99'
            value={value}
            onValueChange={(value) => onChange(value.value)}
        />;
}