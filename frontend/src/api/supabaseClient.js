
import { createClient } from '@supabase/supabase-js';


const supabaseUrl = 'https://qwebmdoupggfcufaxzsn.supabase.co';
const supabaseKey = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF3ZWJtZG91cGdnZmN1ZmF4enNuIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ4MDExODMsImV4cCI6MjA2MDM3NzE4M30.xZeXNH3D0TIEODcsaGVO8F7KLbNaXn2f5ESbpbPkR04';

export const supabase = createClient(supabaseUrl, supabaseKey);
